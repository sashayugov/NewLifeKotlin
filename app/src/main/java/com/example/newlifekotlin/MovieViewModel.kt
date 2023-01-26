package com.example.newlifekotlin

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newlifekotlin.impl.movieRepo.RetrofitMovieRepoImpl
import com.example.newlifekotlin.impl.moviesData.NowPlayingMoviesDataImpl
import com.example.newlifekotlin.impl.moviesData.RoomMoviesData
import com.example.newlifekotlin.impl.moviesData.SearchMoviesDataImpl
import com.example.newlifekotlin.impl.moviesData.UpcomingMoviesDataImpl
import com.example.newlifekotlin.model.MovieCard
import com.example.newlifekotlin.model.MovieRepo

class MovieViewModel(
    private val movieModel: MovieRepo,
    var roomMoviesData: RoomMoviesData
) :
    ViewModel() {

    val upcomingLive = MutableLiveData<UpcomingMoviesDataImpl>()

    val nowPlayingLive = MutableLiveData<NowPlayingMoviesDataImpl>()
    val searchListLive = MutableLiveData<SearchMoviesDataImpl>()
    val roomMoviesLive = MutableLiveData<Array<MovieCard>>()
    val roomMoviesNoteLive = MutableLiveData <String>()

    init {
        Thread {
            val nowPlayingMoviesData = movieModel.getNowPlayingMoviesRepo()
            Handler(Looper.getMainLooper()).post {
                nowPlayingLive.value = nowPlayingMoviesData
            }
        }.start()

        movieModel.getUpcomingMoviesRepo {
            upcomingLive.value = it
        }

        doNoteMovieLive()
    }

    fun doSearchMovieLive(searchValue: String, kidsMode: Boolean) {
        when (kidsMode) {
            true -> movieModel.getKidsSearchMoviesRepo(searchValue) {
                searchListLive.value = it
            }
            false -> movieModel.getSearchMoviesRepo(searchValue) {
                searchListLive.value = it
            }
        }
    }

    fun doNoteMovieLive() {
        roomMoviesData.getAll {
            roomMoviesLive.value = it
        }
    }

    fun doMovieNote(movieCard: MovieCard, note: String) {
        movieCard.movieNote = note
        roomMoviesData.checkNote(movieCard.id) {
            if (it > 0) {
                roomMoviesData.update(movieCard)
                doNoteMovieLive()
            } else {
                roomMoviesData.insert(movieCard)
                doNoteMovieLive()
            }
        }
    }

    fun openMovieNote(movieCard: MovieCard) {
        roomMoviesData.checkNote(movieCard.id) { it ->
            if (it > 0) {
                roomMoviesData.getById(movieCard.id) {
                    roomMoviesNoteLive.value = it.movieNote
                }
            } else {
                roomMoviesNoteLive.value = ""
            }
        }
    }

    fun deleteMovieNote(movieCard: MovieCard) {
        roomMoviesData.delete(movieCard)
        doNoteMovieLive()
    }
}
