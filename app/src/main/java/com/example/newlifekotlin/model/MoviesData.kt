package com.example.newlifekotlin.model

interface MoviesData {

    fun getId(position: Int): String

    fun size(): Int

    fun getMoviesTitle(position: Int): String

    fun getFilmRelease(position: Int): String

    fun getFilmScore(position: Int): String?

    fun getFilmDescription(position: Int): String

    fun getFilmPoster(position: Int): String?

    fun getFilmNote(position: Int): String
}
