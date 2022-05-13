package com.nullpointer.moviescompose.data.local.datasource

import com.nullpointer.moviescompose.models.MovieDB
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    val listMoviesTopRated: Flow<List<MovieDB>>
    val listMoviesPopular: Flow<List<MovieDB>>
    val listMoviesUpComing: Flow<List<MovieDB>>

    suspend fun updateAllTopRated(listMovies: List<MovieDB>)
    suspend fun updateAllPopular(listMovies: List<MovieDB>)
    suspend fun updateAllUpcoming(listMovies: List<MovieDB>)
}