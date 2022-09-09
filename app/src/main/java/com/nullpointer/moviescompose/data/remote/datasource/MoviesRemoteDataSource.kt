package com.nullpointer.moviescompose.data.remote.datasource

import com.nullpointer.moviescompose.models.Cast
import com.nullpointer.moviescompose.models.MovieDB
import com.nullpointer.moviescompose.models.apiResponse.movie.MovieDTO

interface MoviesRemoteDataSource{
    suspend fun getUpComingMovies():List<MovieDB>
    suspend fun getPopularMovies():List<MovieDB>
    suspend fun getToRateMovies(): List<MovieDB>
    suspend fun getMoviesForSearch(query: String): List<MovieDTO>
    suspend fun getCreditsToMovie(idMovie: Long): List<Cast>
}