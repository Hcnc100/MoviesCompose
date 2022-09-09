package com.nullpointer.moviescompose.domain

import com.nullpointer.moviescompose.models.Cast
import com.nullpointer.moviescompose.models.MovieDB
import com.nullpointer.moviescompose.models.apiResponse.MovieApiResponse
import com.nullpointer.moviescompose.models.apiResponse.movie.MovieDTO
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    val listMoviesPopular:Flow<List<MovieDB>>
    val listMoviesUpComing:Flow<List<MovieDB>>
    val listMoviesTopRated:Flow<List<MovieDB>>

    suspend fun updateAllPopular():Int
    suspend fun updateAllUpcoming():Int
    suspend fun updateAllTopRated():Int
    suspend fun getCastFromMovie(idMovie:Long):List<Cast>
    suspend fun getMoviesForSearch(query:String):List<MovieDTO>
}