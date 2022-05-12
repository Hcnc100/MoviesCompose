package com.nullpointer.moviescompose.data.local

import androidx.room.Dao
import androidx.room.Query
import com.nullpointer.moviescompose.models.MovieDB
import com.nullpointer.moviescompose.models.TypeMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {

    @Query("SELECT * FROM table_movies WHERE typeMovie=:type")
    suspend fun getTopMovieRated(type: TypeMovie = TypeMovie.TOP_RATED): Flow<List<MovieDB>>

    @Query("SELECT * FROM table_movies WHERE typeMovie=:type")
    suspend fun getPopularMovie(type: TypeMovie = TypeMovie.POPULAR): Flow<List<MovieDB>>

    @Query("SELECT * FROM table_movies WHERE typeMovie=:type")
    suspend fun getUpcomingMovie(type: TypeMovie = TypeMovie.UP_COMING): Flow<List<MovieDB>>
}