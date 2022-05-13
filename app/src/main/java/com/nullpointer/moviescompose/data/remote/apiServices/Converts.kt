package com.nullpointer.moviescompose.data.remote.apiServices

import androidx.room.TypeConverter
import com.nullpointer.moviescompose.models.TypeMovie

object Converts {
    @TypeConverter
    fun fromTypeMovie(type:TypeMovie):String =
        type.name

    @TypeConverter
    fun toTypeMovie(type:String):TypeMovie =
        TypeMovie.valueOf(type)
}