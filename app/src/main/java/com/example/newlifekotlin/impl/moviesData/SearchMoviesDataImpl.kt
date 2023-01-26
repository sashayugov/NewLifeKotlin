package com.example.newlifekotlin.impl.moviesData

import com.example.newlifekotlin.model.MoviesData
import com.example.newlifekotlin.model.SearchMovieCard

class SearchMoviesDataImpl(var moviesCards: Array<SearchMovieCard>) : MoviesData {

    override fun getId(position: Int): String = moviesCards[position].id

    override fun size(): Int = moviesCards.size

    override fun getMoviesTitle(position: Int): String = moviesCards[position].name

    override fun getFilmRelease(position: Int): String = moviesCards[position].year

    override fun getFilmScore(position: Int): String = moviesCards[position].rating.imdb

    override fun getFilmDescription(position: Int): String = moviesCards[position].description

    override fun getFilmPoster(position: Int): String? = moviesCards[position].poster?.url

    override fun getFilmNote(position: Int): String = moviesCards[position].movieNote
}
