package com.nullpointer.moviescompose.domain

import com.nullpointer.moviescompose.core.utils.InternetCheck
import com.nullpointer.moviescompose.core.utils.NetWorkException
import com.nullpointer.moviescompose.core.utils.TimeOutException
import com.nullpointer.moviescompose.data.local.MovieLocalDataSource
import com.nullpointer.moviescompose.data.remote.MoviesRemoteDataSource
import com.nullpointer.moviescompose.models.MovieDB
import com.nullpointer.moviescompose.models.TypeMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withTimeoutOrNull

class MoviesRepoImpl(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource: MovieLocalDataSource,
) : MoviesRepository {
    override val listMoviesPopular: Flow<List<MovieDB>> = moviesLocalDataSource.listMoviesPopular
    override val listMoviesUpComing: Flow<List<MovieDB>> = moviesLocalDataSource.listMoviesUpComing
    override val listMoviesTopRated: Flow<List<MovieDB>> = moviesLocalDataSource.listMoviesTopRated

    override suspend fun updateAllPopular(): Int {
        if(!InternetCheck.isNetworkAvailable()) throw NetWorkException()
        val listResponse= withTimeoutOrNull(3_000){
            moviesRemoteDataSource.getPopularMovies()
        } ?: throw TimeOutException()
        val listPopular=listResponse.map { MovieDB.fromMovieApi(it,TypeMovie.POPULAR) }
        if(listPopular.isNotEmpty()) moviesLocalDataSource.updateAllPopular(listPopular)
        return listPopular.size
    }

    override suspend fun updateAllUpcoming():Int {
        if(!InternetCheck.isNetworkAvailable()) throw NetWorkException()
        val listResponse= withTimeoutOrNull(3_000){
            moviesRemoteDataSource.getUpComingMovies()
        } ?: throw TimeOutException()
        val listUpcoming=listResponse.map { MovieDB.fromMovieApi(it,TypeMovie.UP_COMING) }
        if(listUpcoming.isNotEmpty()) moviesLocalDataSource.updateAllUpcoming(listUpcoming)
        return listUpcoming.size
    }

    override suspend fun updateAllTopRated():Int {
        if(!InternetCheck.isNetworkAvailable()) throw NetWorkException()
        val listResponse= withTimeoutOrNull(3_000){
            moviesRemoteDataSource.getToRateMovies()
        } ?: throw TimeOutException()
        val listTopRated=listResponse.map { MovieDB.fromMovieApi(it,TypeMovie.TOP_RATED) }
        if(listTopRated.isNotEmpty()) moviesLocalDataSource.updateAllTopRated(listTopRated)
        return listTopRated.size
    }
}