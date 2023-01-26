package com.example.newlifekotlin.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.IMultiInstanceInvalidationCallback
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.newlifekotlin.model.MovieCard

@Dao
interface MovieDao {
    @Query("SELECT * FROM `Movie cards`")
    fun getAll(): Array<MovieCard>

    @Query("SELECT * FROM `Movie cards` WHERE id = :id")
    fun getById(id: String): MovieCard

    @Query("SELECT COUNT(id) FROM `Movie cards` WHERE id = :id")
    fun checkNote(id: String): Int

    @Insert
    fun insert(movieCard: MovieCard)

    @Update
    fun update(movieCard: MovieCard)

    @Delete
    fun delete(movieCard: MovieCard)


}