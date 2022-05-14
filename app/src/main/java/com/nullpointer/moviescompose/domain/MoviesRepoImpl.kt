package com.nullpointer.moviescompose.domain

import com.nullpointer.moviescompose.core.constants.Constants
import com.nullpointer.moviescompose.data.local.datasource.MovieLocalDataSource
import com.nullpointer.moviescompose.data.remote.datasource.MoviesRemoteDataSource
import com.nullpointer.moviescompose.models.Cast
import com.nullpointer.moviescompose.models.MovieDB
import com.nullpointer.moviescompose.models.apiResponse.MovieApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepoImpl @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource: MovieLocalDataSource,
) : MoviesRepository {
    override val listMoviesPopular: Flow<List<MovieDB>> = moviesLocalDataSource.listMoviesPopular
    override val listMoviesUpComing: Flow<List<MovieDB>> = moviesLocalDataSource.listMoviesUpComing
    override val listMoviesTopRated: Flow<List<MovieDB>> = moviesLocalDataSource.listMoviesTopRated

    override suspend fun updateAllPopular(): Int {
        val listPopular = moviesRemoteDataSource.getPopularMovies()
        if (listPopular.isNotEmpty()) moviesLocalDataSource.updateAllPopular(listPopular)
        return listPopular.size
    }

    override suspend fun updateAllUpcoming(): Int {
        val listUpcoming = moviesRemoteDataSource.getUpComingMovies()
        if (listUpcoming.isNotEmpty()) moviesLocalDataSource.updateAllUpcoming(listUpcoming)
        return listUpcoming.size
    }

    override suspend fun updateAllTopRated(): Int {
        val listTopRated = moviesRemoteDataSource.getToRateMovies()
        if (listTopRated.isNotEmpty()) moviesLocalDataSource.updateAllTopRated(listTopRated)
        return listTopRated.size
    }

    override suspend fun getCastFromMovie(idMovie: Long): List<Cast> =
        moviesRemoteDataSource.getCreditsToMovie(idMovie)

    override suspend fun getMoviesForSearch(query: String): List<MovieApiResponse.Movie> {
        return moviesRemoteDataSource.getMoviesForSearch(query).map {
            it.copy(poster_path = "${Constants.PREFIX_IMG_URL}${it.poster_path}")
        }
    }

}