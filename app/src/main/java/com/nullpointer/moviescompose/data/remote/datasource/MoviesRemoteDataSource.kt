package com.nullpointer.moviescompose.data.remote.datasource

import com.nullpointer.moviescompose.models.MovieDB

interface MoviesRemoteDataSource{
    suspend fun getUpComingMovies():List<MovieDB>
    suspend fun getPopularMovies():List<MovieDB>
    suspend fun getToRateMovies():List<MovieDB>
}