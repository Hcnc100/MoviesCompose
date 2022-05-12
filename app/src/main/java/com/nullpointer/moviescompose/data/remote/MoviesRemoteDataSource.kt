package com.nullpointer.moviescompose.data.remote

class MoviesRemoteDataSource(
    private val moviesApiServices: MoviesApiServices,
) {
    suspend fun getUpComingMovies() = moviesApiServices.getUpcomingMovies()

    suspend fun getPopularMovies() = moviesApiServices.getPopularMovies()

    suspend fun getToRateMovies() = moviesApiServices.getTopRatedMovies()
}