package com.example.newlifekotlin.impl.movieRepo

import android.os.Handler

import android.os.Looper
import com.example.newlifekotlin.*
import com.example.newlifekotlin.impl.moviesData.NowPlayingMoviesDataImpl
import com.example.newlifekotlin.impl.moviesData.SearchMoviesDataImpl
import com.example.newlifekotlin.impl.moviesData.UpcomingMoviesDataImpl
import com.example.newlifekotlin.model.*
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WebMovieRepoImpl : MovieRepo {

    private var unixTime = System.currentTimeMillis() / 1000L

    override fun getNowPlayingMoviesRepo(): NowPlayingMoviesDataImpl {
        val gson by lazy { Gson() }
        var nowMovieURL =
            "https://kudago.com/public-api/v1.4/movies/?fields=id,title,description,imdb_rating,publication_date,year&actual_until=$unixTime&location=msk&order_by=-publication_date"
        var url = URL(nowMovieURL)
        lateinit var moviesCards: Array<MovieCard>
        try {
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5_000
            var bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
            var results = bufferedReader.readLines().toString()
            var reposArray = gson.fromJson(results, Array<MovieDTO>::class.java)
            connection.disconnect()
            moviesCards = reposArray[0].results
        } catch (e: IOException) {
            moviesCards = Array(1) {
                MovieCard(
                    "",
                    "NO INTERNET",
                    "",
                    "",
                    PosterDTO("https://i.imgur.com/DvpvklR.png"),
                    "",
                    ""
                )
            }
        }
        return NowPlayingMoviesDataImpl(moviesCards)
    }

    override fun getUpcomingMoviesRepo(callback: (UpcomingMoviesDataImpl) -> Unit) {
        val gson by lazy { Gson() }
        val nowMovieURL =
            "https://kudago.com/public-api/v1.4/movies/?fields=id,title,description,imdb_rating,publication_date,year&actual_since=$unixTime&location=msk&order_by=-publication_date"
        val url = URL(nowMovieURL)
        lateinit var moviesCards: Array<MovieCard>
        try {
            Thread {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5_000
                val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                val results = bufferedReader.readLines().toString()
                val reposArray = gson.fromJson(results, Array<MovieDTO>::class.java)
                connection.disconnect()
                moviesCards = reposArray[0].results
                Handler(Looper.getMainLooper()).post {
                    callback.invoke(UpcomingMoviesDataImpl(moviesCards))
                }
            }.start()
        } catch (e: IOException) {
            moviesCards = Array(1) {
                MovieCard(
                    "",
                    "NO INTERNET",
                    "",
                    "",
                    PosterDTO("https://i.imgur.com/DvpvklR.png"),
                    "",
                    ""
                )
            }
        }
    }

    override fun getKidsSearchMoviesRepo(
        searchValue: String,
        callback: (SearchMoviesDataImpl) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getSearchMoviesRepo(
        searchValue: String,
        callback: (SearchMoviesDataImpl) -> Unit
    ) {
        val moviesCards = Array(50) {
            SearchMovieCard(
                "",
                "Movie",
                "2023",
                "Good movie",
                SearchMovieCard.RatingDTO("8.1"),
                SearchMovieCard.ImageDTO("https://i.imgur.com/DvpvklR.png"),
                ""
            )
        }
        callback.invoke(SearchMoviesDataImpl(moviesCards))
    }
}