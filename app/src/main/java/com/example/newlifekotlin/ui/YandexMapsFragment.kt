package com.example.newlifekotlin.ui

import android.Manifest
import com.yandex.mapkit.GeoObjectCollection
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.newlifekotlin.BuildConfig
import com.example.newlifekotlin.R
import com.example.newlifekotlin.databinding.FragmentYandexMapsBinding
import com.google.android.material.snackbar.Snackbar
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.*
import com.yandex.mapkit.search.Session.SearchListener
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.network.NetworkError
import com.yandex.runtime.network.RemoteError
import com.example.newlifekotlin.MapKitInitializer as MapKitInitializer

private const val MAPKIT_API_KEY = BuildConfig.MAPKIT_API_KEY
private const val QUERY = "кинотеатр"

class YandexMapsFragment : Fragment(), SearchListener, CameraListener {

    private var _binding: FragmentYandexMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mapView: MapView

    private val searchManager by lazy {
        SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)
    }

    private val searchSession by lazy {
        searchManager.submit(
            QUERY, VisibleRegionUtils.toPolygon(mapView.map.visibleRegion),
            SearchOptions(),
            this
        )
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                doPositionByGps()
                doUserLocation()
            }
            else noGpsPermission()
        }

    private val locationManagerByMapKit by lazy {
        MapKitFactory.getInstance().createLocationManager()
    }
    private val locationManager by lazy { requireContext().getSystemService(LOCATION_SERVICE) as LocationManager }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitInitializer.initialize(MAPKIT_API_KEY, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYandexMapsBinding.inflate(inflater, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SearchFactory.initialize(requireContext())
        mapView = binding.mapView
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        mapView.map.addCameraListener(this)
    }

    private fun doPositionByGps() {
        openLastPosition()
        locationManagerByMapKit.requestSingleUpdate(object : LocationListener {
            override fun onLocationUpdated(p0: Location) {
                openMapPosition(p0.position)
            }

            override fun onLocationStatusUpdated(p0: LocationStatus) {
            }
        })
    }

    private fun openLastPosition() {
        if (requireContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            != PERMISSION_GRANTED
        )
            return noGpsPermission()
        val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (lastLocation != null) {
            openMapPosition(Point(lastLocation.latitude, lastLocation.longitude))
        }
    }

    private fun openMapPosition(point: Point) {
        mapView.map.move(
            CameraPosition(point, 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 5F),
            null
        )
    }

    private fun doUserLocation() {
        val userLocationLayer =
            MapKitFactory.getInstance().createUserLocationLayer(mapView.mapWindow)
        userLocationLayer.isVisible = true
    }

    private fun noGpsPermission() {
        Snackbar.make(
            requireView(),
            "Sorry, we can't find cinemas without GPS",
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onSearchResponse(response: Response) {
        val mapObjects = mapView.map.mapObjects
        mapObjects.clear()

        for (searchResult: GeoObjectCollection.Item in response.collection.children) {
            val resultLocation = searchResult.obj?.geometry?.first()?.point
            if (resultLocation != null) {
                mapObjects.addPlacemark(
                    resultLocation, ImageProvider.fromResource(
                        requireContext(),
                        R.drawable.search_layer_pin_selected_default
                    )
                )
            }
        }
    }

    override fun onSearchError(error: Error) {
        when (error) {
            RemoteError { true } -> Toast.makeText(
                requireContext(),
                "Remote Error",
                Toast.LENGTH_LONG
            ).show()
            NetworkError { true } -> Toast.makeText(
                requireContext(),
                "Network Error",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCameraPositionChanged(
        map: Map,
        cameraPosition: CameraPosition,
        cameraUpdateReason: CameraUpdateReason,
        finished: Boolean
    ) {
        if (finished) {
            searchSession.setSearchArea(VisibleRegionUtils.toPolygon(mapView.map.visibleRegion))
            searchSession.resubmit(this)
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
