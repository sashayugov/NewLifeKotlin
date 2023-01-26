package com.example.newlifekotlin.impl.retrofit

import com.example.newlifekotlin.model.MovieDTO
import com.example.newlifekotlin.model.SearchMovieDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitMovieService {
    @GET("public-api/v1.4/movies/?fields=id,title,description,imdb_rating,publication_date,year,poster&location=msk&order_by=-publication_date")
    fun listNowPlayingMovies(@Query("actual_until") unixTime: String): Call<MovieDTO>

    @GET("public-api/v1.4/movies/?fields=id,title,description,imdb_rating,publication_date,year,poster&location=msk&order_by=-publication_date")
    fun listUpcomingMovies(@Query("actual_since") unixTime: String): Call<MovieDTO>

    @GET("movie?token=ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06&isStrict=false&sortField=year&sortType=-1&limit=50&field=typeNumber&search=3&field=name&")
    fun listSearchKidsMovies(@Query("search") name: String): Call<SearchMovieDTO>

    @GET("movie?token=ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06&isStrict=false&sortField=year&sortType=-1&limit=50&field=name&")
    fun listSearchMovies(@Query("search") name: String): Call<SearchMovieDTO>
}