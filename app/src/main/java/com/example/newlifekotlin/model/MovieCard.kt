package com.example.newlifekotlin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Movie cards")
data class MovieCard(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "year")
    var year: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "poster")
    var poster: PosterDTO?,
    @ColumnInfo(name = "rating")
    @SerializedName("imdb_rating")
    var imdbRating: String?,
    @ColumnInfo(name = "note")
    var movieNote: String
)


