package com.example.newlifekotlin.impl.movieRepo

import com.example.newlifekotlin.impl.moviesData.NowPlayingMoviesDataImpl
import com.example.newlifekotlin.impl.moviesData.SearchMoviesDataImpl
import com.example.newlifekotlin.impl.moviesData.UpcomingMoviesDataImpl
import com.example.newlifekotlin.model.MovieCard
import com.example.newlifekotlin.model.MovieRepo
import com.example.newlifekotlin.model.PosterDTO
import com.example.newlifekotlin.model.SearchMovieCard

class MockMovieRepoImpl : MovieRepo {

    override fun getNowPlayingMoviesRepo(): NowPlayingMoviesDataImpl {
        val moviesCards = Array(30) { MovieCard("","Movie", "2022", "Good Movie", PosterDTO("https://i.imgur.com/DvpvklR.png"), "", "8.1") }
        return NowPlayingMoviesDataImpl(moviesCards)
    }

    override fun getUpcomingMoviesRepo(callback: (UpcomingMoviesDataImpl) -> Unit) {
        val moviesCards = Array(50) { MovieCard("","Movie", "2023", "Good movie", PosterDTO("https://i.imgur.com/DvpvklR.png"), "","") }
        callback.invoke(UpcomingMoviesDataImpl(moviesCards))
    }

    override fun getKidsSearchMoviesRepo(
        searchValue: String,
        callback: (SearchMoviesDataImpl) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getSearchMoviesRepo(searchValue: String, callback: (SearchMoviesDataImpl) -> Unit) {
        val moviesCards = Array(50) { SearchMovieCard("","Movie", "2023", "Good movie", SearchMovieCard.RatingDTO("8.1"), SearchMovieCard.ImageDTO("https://i.imgur.com/DvpvklR.png"), "") }
        callback.invoke(SearchMoviesDataImpl(moviesCards))    }
}