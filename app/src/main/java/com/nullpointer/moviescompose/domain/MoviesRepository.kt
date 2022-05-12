package com.nullpointer.moviescompose.domain

import com.nullpointer.moviescompose.models.MovieDB
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    val listMoviesPopular:Flow<List<MovieDB>>
    val listMoviesUpComing:Flow<List<MovieDB>>
    val listMoviesTopRated:Flow<List<MovieDB>>

    suspend fun updateAllPopular():Int
    suspend fun updateAllUpcoming():Int
    suspend fun updateAllTopRated():Int
}