package com.example.newlifekotlin.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.newlifekotlin.MovieViewModel
import com.example.newlifekotlin.databinding.FragmentMovieNoteDescriptionBinding
import com.example.newlifekotlin.model.MovieCard

private const val MOVIE_INDEX = "MOVIE_INDEX"

class MovieNoteDescriptionFragment : Fragment() {

    private var _binding: FragmentMovieNoteDescriptionBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieDescription: TextView
    private lateinit var etMovieNote: EditText
    private lateinit var tvMovieNote: TextView
    private lateinit var buttonSaveNote: Button
    private lateinit var buttonDeleteNote: Button

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
        _binding = FragmentMovieNoteDescriptionBinding.inflate(inflater, container, false)
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
        tvMovieNote = binding.tvMovieNote
        buttonSaveNote = binding.saveNote
        buttonDeleteNote = binding.deleteNote
    }

    private fun onOpenFragment() {
        movieViewModel.roomMoviesLive.observe(viewLifecycleOwner, Observer { movieCards ->

            movieDescription.text =
                movieCards[movieIndex].description.removeSurrounding("<p>", "</p>")

            movieViewModel.openMovieNote(movieCards[movieIndex])
            movieViewModel.roomMoviesNoteLive.observe(viewLifecycleOwner, Observer {
                 tvMovieNote.text = it
            })
            makeNote(movieCards)
            deleteNote(movieCards)
        })
    }

    private fun makeNote(movieCards: Array<MovieCard>) {
        buttonSaveNote.setOnClickListener {
            val note = etMovieNote.text
            movieViewModel.doMovieNote(movieCards[movieIndex], note.toString())
        }
    }

    private fun deleteNote(movieCards: Array<MovieCard>) {
        buttonDeleteNote.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage("Delete movie note?")
                .setCancelable(true)
                .setPositiveButton("OK") { _, _ ->
                    movieViewModel.deleteMovieNote(movieCards[movieIndex])
                    requireActivity().supportFragmentManager.popBackStack()
                }
                .setNegativeButton("Cancel") { _, _ ->
                }
                .show()
        }
    }

    companion object {
        fun newInstance(movieIndex: Int) =
            MovieNoteDescriptionFragment().apply {
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

