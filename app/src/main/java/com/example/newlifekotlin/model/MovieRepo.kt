package com.example.newlifekotlin.model

import com.example.newlifekotlin.impl.moviesData.SearchMoviesDataImpl
import com.example.newlifekotlin.impl.moviesData.NowPlayingMoviesDataImpl
import com.example.newlifekotlin.impl.moviesData.UpcomingMoviesDataImpl

interface MovieRepo {
    fun getNowPlayingMoviesRepo(): NowPlayingMoviesDataImpl
    fun getUpcomingMoviesRepo(callback: (UpcomingMoviesDataImpl) -> Unit)
    fun getKidsSearchMoviesRepo(searchValue: String, callback: (SearchMoviesDataImpl) -> Unit)
    fun getSearchMoviesRepo(searchValue: String, callback: (SearchMoviesDataImpl) -> Unit)
}
