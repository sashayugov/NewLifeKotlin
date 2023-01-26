package com.example.newlifekotlin.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newlifekotlin.MovieViewModel
import com.example.newlifekotlin.SearchMoviesCardAdapter
import com.example.newlifekotlin.databinding.FragmentSearchMovieBinding

private const val ADULT_SWITCH_VALUE = "ADULT_SWITCH_VALUE"

class SearchMovieFragment : Fragment() {

    private var _binding: FragmentSearchMovieBinding? = null
    private val binding get() = _binding!!

    private val movieViewModel: MovieViewModel by activityViewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var switchAdult: SwitchCompat



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchAdult = binding.switchAdultContent
        sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)!!
        switchAdult.isChecked = sharedPreferences.getBoolean(ADULT_SWITCH_VALUE, false)
        init()
    }

    private fun init() {
        val searchValue = binding.searchMovie
        binding.startSearchButton.setOnClickListener {
            val textValue = searchValue.text
            movieViewModel.doSearchMovieLive(textValue.toString(), switchAdult.isChecked)
            doSearchMovieList()
        }
    }


    private fun doSearchMovieList() {
        movieViewModel.searchListLive.observe(viewLifecycleOwner, Observer {
            binding.foundList.layoutManager = LinearLayoutManager(requireContext())
            val searchMoviesAdapter =
                SearchMoviesCardAdapter(it, object : SearchMoviesCardAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        TODO("Not yet implemented")
                    }
                })
            binding.foundList.adapter = searchMoviesAdapter
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        sharedPreferences.edit().let {
            it.putBoolean(ADULT_SWITCH_VALUE, switchAdult.isChecked)
            it.commit()
        }
    }
}