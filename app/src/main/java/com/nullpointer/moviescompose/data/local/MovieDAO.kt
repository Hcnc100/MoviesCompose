package com.nullpointer.moviescompose.data.local

import androidx.room.*
import com.nullpointer.moviescompose.models.MovieDB
import com.nullpointer.moviescompose.models.TypeMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {

    @Query("SELECT * FROM table_movies WHERE typeMovie=:type")
    fun getMoviesByType(type: TypeMovie): Flow<List<MovieDB>>

    @Query("DELETE FROM table_movies WHERE typeMovie=:type")
    suspend fun deleterAllMoviesByType(type: TypeMovie = TypeMovie.TOP_RATED)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertListMovies(listMovies: List<MovieDB>)

    @Transaction
    suspend fun updateAllMoviesByType(listMovies: List<MovieDB>, type: TypeMovie) {
        deleterAllMoviesByType(type)
        insertListMovies(listMovies)
    }
}