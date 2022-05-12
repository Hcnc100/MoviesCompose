package com.nullpointer.moviescompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nullpointer.moviescompose.data.remote.Converts
import com.nullpointer.moviescompose.models.MovieDB

@Database(
    entities = [MovieDB::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converts::class)
abstract class MovieDataBase:RoomDatabase() {

    abstract fun getMoviesDao():MovieDAO
}