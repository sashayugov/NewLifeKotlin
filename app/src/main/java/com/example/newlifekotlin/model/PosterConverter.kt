package com.example.newlifekotlin.model

import androidx.room.TypeConverter

class PosterConverter {
    @TypeConverter
    fun fromPoster(poster: PosterDTO): String {
        return poster.image
    }

    @TypeConverter
    fun toPoster(imageUrl: String): PosterDTO {
        return PosterDTO(imageUrl)
    }
}