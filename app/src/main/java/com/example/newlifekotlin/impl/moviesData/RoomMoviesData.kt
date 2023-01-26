package com.example.newlifekotlin.impl.moviesData

import android.content.Context
import android.graphics.Movie
import android.os.Looper
import androidx.room.Room
import com.example.newlifekotlin.impl.MovieRoomDb
import com.example.newlifekotlin.model.MovieCard
import com.example.newlifekotlin.model.MovieDao
import kotlinx.coroutines.flow.callbackFlow
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class RoomMoviesData(context: Context) {
    private val movieDao: MovieDao =
        Room.databaseBuilder(context, MovieRoomDb::class.java, "movies.db").build().movieDao()

    fun getAll(callback: (Array<MovieCard>) -> Unit) {
        Thread {
            val array = movieDao.getAll()
            Handler(Looper.getMainLooper()).post { callback.invoke(array) }
        }.start()
    }

    fun getById(id: String, callback: (MovieCard) -> Unit) {
        Thread {
            val movieCard = movieDao.getById(id)
            Handler(Looper.getMainLooper()).post {
                callback.invoke(movieCard)
            }
        }.start()
    }

    fun checkNote(id: String, callback: (Int) -> Unit) {
        Thread {
            val count = movieDao.checkNote(id)
            Handler(Looper.getMainLooper()).post { callback.invoke(count) }
        }.start()
    }

    fun insert(movieCard: MovieCard) {
        Thread {
            movieDao.insert(movieCard)
        }.start()
    }

    fun update(movieCard: MovieCard) {
        Thread {
            movieDao.update(movieCard)
        }.start()
    }

    fun delete(movieCard: MovieCard) {
        Thread {
            movieDao.delete(movieCard)
        }.start()
    }
}