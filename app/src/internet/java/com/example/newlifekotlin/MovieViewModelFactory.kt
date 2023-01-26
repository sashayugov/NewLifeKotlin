package com.example.newlifekotlin

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newlifekotlin.impl.movieRepo.MockMovieRepoImpl
import com.example.newlifekotlin.impl.movieRepo.RetrofitMovieRepoImpl
import com.example.newlifekotlin.impl.moviesData.RoomMoviesData

class MovieViewModelFactory(val context: Context) : ViewModelProvider.Factory {

    private val movieModel = RetrofitMovieRepoImpl()
    private val roomMoviesData = RoomMoviesData(context)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieViewModel(movieModel, roomMoviesData) as T
    }
}