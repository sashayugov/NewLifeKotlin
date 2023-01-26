package com.example.newlifekotlin.ui

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newlifekotlin.*
import com.example.newlifekotlin.databinding.ActivityMainBinding
import com.example.newlifekotlin.model.MyBroadcastReceiver

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val movieViewModel: MovieViewModel by viewModels { MovieViewModelFactory(this) }
    private val receiver = MyBroadcastReceiver()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createBottomNavigationView()
        doNowMoviesList()
        doUpcomingMovieList()
        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun doNowMoviesList() {
        movieViewModel.nowPlayingLive.observe(this) {
            binding.nowPlaying.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val nowPlayingMovieCardAdapter = NowPlayingMovieCardAdapter(it,
                object : NowPlayingMovieCardAdapter.OnItemClickListener {
                    override fun onTitleClick(position: Int) {
                        openFragment(MovieDescriptionFragment.newInstance(position))
                    }
                    override fun onImageClick(position: Int) {
                        openFragment(MovieImageFragment.newInstance(it.getFilmPoster(position)))
                    }
                })
            binding.nowPlaying.adapter = nowPlayingMovieCardAdapter
            binding.progressBar2.hide()
        }
    }

    private fun doUpcomingMovieList() {
        movieViewModel.upcomingLive.observe(this) {
            binding.upcoming.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val upcomingMovieCardAdapter = UpcomingMovieCardAdapter(it,
                object : UpcomingMovieCardAdapter.OnItemClickListener {
                    override fun onTitleClick(position: Int) {
                        openFragment(UpcomingMovieDescriptionFragment.newInstance(position))
                    }

                    override fun onImageClick(position: Int) {
                        openFragment(MovieImageFragment.newInstance(it.getFilmPoster(position)))
                    }
                })
            binding.upcoming.adapter = upcomingMovieCardAdapter
            binding.progressBar.hide()
        }
    }

    fun <T: Fragment> openFragment(fragment: T) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_activity, fragment, null)
            .addToBackStack(null)
            .commit()
    }

    private fun createBottomNavigationView() {
        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_button -> {
                    supportFragmentManager.popBackStack()
                    true
                }
                R.id.note_button -> {
                    openFragment(MovieNotesListFragment())
                    true
                }
                R.id.search_button -> {
                    openFragment(SearchMovieFragment())
                    true
                }
                R.id.cinemas_button -> {
                    openFragment(YandexMapsFragment())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}
