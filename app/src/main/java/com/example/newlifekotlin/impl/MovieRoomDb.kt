package com.example.newlifekotlin.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newlifekotlin.model.MovieCard
import com.example.newlifekotlin.model.MovieDao
import com.example.newlifekotlin.model.PosterConverter

@Database(entities = [MovieCard::class], version = 1)
@TypeConverters(PosterConverter::class)
abstract class MovieRoomDb : RoomDatabase() {
    abstract fun movieDao(): MovieDao

}