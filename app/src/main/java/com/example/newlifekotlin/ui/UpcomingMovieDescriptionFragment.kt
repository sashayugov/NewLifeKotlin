package com.example.newlifekotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.newlifekotlin.MovieViewModel
import com.example.newlifekotlin.databinding.FragmentMovieDescriptionBinding
import com.example.newlifekotlin.impl.moviesData.UpcomingMoviesDataImpl
import com.example.newlifekotlin.model.MovieCard

private const val MOVIE_INDEX = "MOVIE_INDEX"

class UpcomingMovieDescriptionFragment : Fragment() {

    private var _binding: FragmentMovieDescriptionBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieDescription: TextView
    private lateinit var etMovieNote: EditText
    private lateinit var buttonSaveNote: Button
    private lateinit var tvMovieNote: TextView

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
        onOpenFragment()
    }

    private fun init() {
        movieDescription = binding.movieDescription
        etMovieNote = binding.movieNote
        buttonSaveNote = binding.saveNote
        tvMovieNote = binding.tvMovieNote
    }

    private fun onOpenFragment() {
        movieViewModel.upcomingLive.observe(viewLifecycleOwner, Observer { upcomingMoviesDataImpl ->
            movieDescription.text =
                upcomingMoviesDataImpl.getFilmDescription(movieIndex).removeSurrounding("<p>", "</p>")

            movieViewModel.openMovieNote(upcomingMoviesDataImpl.moviesCards[movieIndex])
            movieViewModel.roomMoviesNoteLive.observe(viewLifecycleOwner, Observer {
                 tvMovieNote.text = it
            })
            makeNote(upcomingMoviesDataImpl)
        })
    }

    private fun makeNote(moviesDataImpl: UpcomingMoviesDataImpl) {
        buttonSaveNote.setOnClickListener {
            val note = etMovieNote.text
            movieViewModel.doMovieNote(moviesDataImpl.moviesCards[movieIndex], note.toString())
        }
    }

    companion object {
        fun newInstance(movieIndex: Int) =
            UpcomingMovieDescriptionFragment().apply {
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

