package com.example.newlifekotlin.impl.movieRepo

import com.example.newlifekotlin.impl.moviesData.NowPlayingMoviesDataImpl
import com.example.newlifekotlin.impl.moviesData.SearchMoviesDataImpl
import com.example.newlifekotlin.impl.moviesData.UpcomingMoviesDataImpl
import com.example.newlifekotlin.impl.retrofit.RetrofitMovieService
import com.example.newlifekotlin.model.MovieDTO
import com.example.newlifekotlin.model.MovieRepo
import com.example.newlifekotlin.model.SearchMovieDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitMovieRepoImpl : MovieRepo {

    private var unixTime = System.currentTimeMillis() / 1000L

    private var retrofitKudaGo: Retrofit = Retrofit.Builder()
        .baseUrl("https://kudago.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var retrofitKinopoisk: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.kinopoisk.dev/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var apiKudaGo: RetrofitMovieService = retrofitKudaGo.create(RetrofitMovieService::class.java)
    private var apiKinopoisk: RetrofitMovieService = retrofitKinopoisk.create(RetrofitMovieService::class.java)


    override fun getNowPlayingMoviesRepo(): NowPlayingMoviesDataImpl {
        val repo = apiKudaGo.listNowPlayingMovies(unixTime.toString()).execute().body()
        return NowPlayingMoviesDataImpl(repo?.results ?: emptyArray())
    }

    override fun getUpcomingMoviesRepo(callback: (UpcomingMoviesDataImpl) -> Unit) {
        val repo = apiKudaGo.listUpcomingMovies(unixTime.toString()).enqueue(object : Callback<MovieDTO> {
            override fun onResponse(call: Call<MovieDTO>, response: Response<MovieDTO>) {
                callback(UpcomingMoviesDataImpl(response.body()?.results ?: emptyArray()))
            }

            override fun onFailure(call: Call<MovieDTO>, t: Throwable) {

            }
        })
    }

    override fun getKidsSearchMoviesRepo(
        searchValue: String,
        callback: (SearchMoviesDataImpl) -> Unit
    ) {
        val repo =
            apiKinopoisk.listSearchKidsMovies(searchValue).enqueue(object : Callback<SearchMovieDTO> {
                override fun onResponse(
                    call: Call<SearchMovieDTO>,
                    response: Response<SearchMovieDTO>
                ) {
                    callback(SearchMoviesDataImpl(response.body()?.docs ?: emptyArray()))
                }

                override fun onFailure(call: Call<SearchMovieDTO>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    override fun getSearchMoviesRepo(
        searchValue: String,
        callback: (SearchMoviesDataImpl) -> Unit
        ) {
        apiKinopoisk.listSearchMovies(searchValue).enqueue(object : Callback<SearchMovieDTO> {
            override fun onResponse(
                call: Call<SearchMovieDTO>,
                response: Response<SearchMovieDTO>
            ) {
                callback(SearchMoviesDataImpl(response.body()?.docs ?: emptyArray()))
            }

            override fun onFailure(call: Call<SearchMovieDTO>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}