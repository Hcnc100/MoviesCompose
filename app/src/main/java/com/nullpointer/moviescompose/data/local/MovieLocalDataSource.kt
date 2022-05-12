package com.nullpointer.moviescompose.data.local

import com.nullpointer.moviescompose.models.MovieDB
import com.nullpointer.moviescompose.models.TypeMovie
import kotlinx.coroutines.flow.Flow

class MovieLocalDataSource(
    private val movieDAO: MovieDAO,
) {
    val listMoviesTopRated: Flow<List<MovieDB>> =
        movieDAO.getMoviesByType(TypeMovie.TOP_RATED)

    val listMoviesPopular: Flow<List<MovieDB>> =
        movieDAO.getMoviesByType(TypeMovie.POPULAR)

    val listMoviesUpComing: Flow<List<MovieDB>> =
        movieDAO.getMoviesByType(TypeMovie.UP_COMING)

    suspend fun updateAllTopRated(listMovies: List<MovieDB>){
        movieDAO.updateAllMoviesByType(listMovies,TypeMovie.TOP_RATED)
    }

    suspend fun updateAllPopular(listMovies: List<MovieDB>){
        movieDAO.updateAllMoviesByType(listMovies,TypeMovie.POPULAR)
    }

    suspend fun updateAllUpcoming(listMovies: List<MovieDB>){
        movieDAO.updateAllMoviesByType(listMovies,TypeMovie.UP_COMING)
    }
}