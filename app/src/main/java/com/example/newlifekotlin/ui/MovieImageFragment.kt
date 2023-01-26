package com.example.newlifekotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newlifekotlin.databinding.FragmentMovieImageBinding
import com.squareup.picasso.Picasso

private const val MOVIE_IMAGE_URL = "MOVIE_IMAGE_URL"

class MovieImageFragment : Fragment() {

    private var _binding: FragmentMovieImageBinding? = null
    private val binding get() = _binding!!

    private var movieImageURL: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieImageURL = it.getString(MOVIE_IMAGE_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val movieImage = binding.movieImageFragment
        Picasso.get().load(movieImageURL)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(movieImage)

        movieImage.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }


    companion object {
        fun newInstance(movieImageURL: String?) =
            MovieImageFragment().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_IMAGE_URL, movieImageURL)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}