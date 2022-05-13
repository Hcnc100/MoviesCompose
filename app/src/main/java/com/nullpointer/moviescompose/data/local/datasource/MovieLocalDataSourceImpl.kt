package com.nullpointer.moviescompose.data.local.datasource

import com.nullpointer.moviescompose.data.local.database.MovieDAO
import com.nullpointer.moviescompose.models.MovieDB
import com.nullpointer.moviescompose.models.TypeMovie
import kotlinx.coroutines.flow.Flow

class MovieLocalDataSourceImpl(
    private val movieDAO: MovieDAO,
):MovieLocalDataSource {
    override val listMoviesTopRated: Flow<List<MovieDB>> =
        movieDAO.getMoviesByType(TypeMovie.TOP_RATED)

    override val listMoviesPopular: Flow<List<MovieDB>> =
        movieDAO.getMoviesByType(TypeMovie.POPULAR)

    override val listMoviesUpComing: Flow<List<MovieDB>> =
        movieDAO.getMoviesByType(TypeMovie.UP_COMING)

    override suspend fun updateAllTopRated(listMovies: List<MovieDB>) {
        movieDAO.updateAllMoviesByType(listMovies, TypeMovie.TOP_RATED)
    }

    override suspend fun updateAllPopular(listMovies: List<MovieDB>) {
        movieDAO.updateAllMoviesByType(listMovies, TypeMovie.POPULAR)
    }

    override suspend fun updateAllUpcoming(listMovies: List<MovieDB>) {
        movieDAO.updateAllMoviesByType(listMovies, TypeMovie.UP_COMING)
    }
}