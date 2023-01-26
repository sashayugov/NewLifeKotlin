package com.example.newlifekotlin.impl.moviesData

import com.example.newlifekotlin.model.MovieCard
import com.example.newlifekotlin.model.MoviesData

class NowPlayingMoviesDataImpl(var moviesCards: Array<MovieCard>) : MoviesData {

    override fun getId(position: Int): String = moviesCards[position].id

    override fun size(): Int = moviesCards.size

    override fun getMoviesTitle(position: Int): String = moviesCards[position].title

    override fun getFilmRelease(position: Int): String = moviesCards[position].year

    override fun getFilmScore(position: Int): String? = moviesCards[position].imdbRating

    override fun getFilmDescription(position: Int): String = moviesCards[position].description

    override fun getFilmPoster(position: Int): String? = moviesCards[position].poster?.image

    override fun getFilmNote(position: Int):  String = moviesCards[position].movieNote
}
