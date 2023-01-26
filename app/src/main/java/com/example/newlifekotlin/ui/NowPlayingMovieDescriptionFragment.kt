package com.example.newlifekotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.newlifekotlin.MovieViewModel
import com.example.newlifekotlin.databinding.FragmentMovieDescriptionBinding

private const val MOVIE_INDEX = "MOVIE_INDEX"

class MovieDescriptionFragment : Fragment() {

    private var _binding: FragmentMovieDescriptionBinding? = null
    private val binding get() = _binding!!

    private var movieIndex: Int = 0
    private val movieViewModel: MovieViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieIndex = it.getInt(MOVIE_INDEX)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val movieDescription: TextView = binding.movieDescription
        movieViewModel.nowPlayingLive.observe(viewLifecycleOwner, Observer {
            movieDescription.text =
                it.getFilmDescription(movieIndex).removeSurrounding("<p>", "</p>")
        })
    }

    companion object {
        fun newInstance(movieIndex: Int) =
            MovieDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_INDEX, movieIndex)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}