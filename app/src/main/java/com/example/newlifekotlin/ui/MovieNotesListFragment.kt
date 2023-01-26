package com.example.newlifekotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newlifekotlin.*
import com.example.newlifekotlin.databinding.FragmentMovieNotesListBinding

class MovieNotesListFragment : Fragment() {

    private var _binding: FragmentMovieNotesListBinding? = null
    private val binding get() = _binding!!

    private val movieViewModel: MovieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doMovieNotesList()
    }

    private fun doMovieNotesList() {
        movieViewModel.doNoteMovieLive()
        movieViewModel.roomMoviesLive.observe(viewLifecycleOwner, Observer {
            binding.movieNotesList.layoutManager = GridLayoutManager(requireContext(), 2)
            val movieNotesCardAdapter =
                MovieNotesCardAdapter(it, object : MovieNotesCardAdapter.OnItemClickListener {
                    override fun onTitleClick(position: Int) {
                        openFragment(MovieNoteDescriptionFragment.newInstance(position))
                    }
                    override fun onImageClick(position: Int) {
                        openFragment(MovieImageFragment.newInstance(it[position].poster?.image))
                    }
                })
            binding.movieNotesList.adapter = movieNotesCardAdapter
        })
    }

    fun <T: Fragment> openFragment(fragment: T) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity, fragment, null)
            .addToBackStack(null)
            .commit()
    }

}