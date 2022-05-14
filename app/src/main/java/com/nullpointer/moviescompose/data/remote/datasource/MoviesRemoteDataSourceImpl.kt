package com.nullpointer.moviescompose.data.remote.datasource

import android.content.Context
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.core.utils.InternetCheck
import com.nullpointer.moviescompose.core.utils.NetWorkException
import com.nullpointer.moviescompose.core.utils.TimeOutException
import com.nullpointer.moviescompose.data.remote.apiServices.MoviesApiServices
import com.nullpointer.moviescompose.models.Cast
import com.nullpointer.moviescompose.models.MovieDB
import com.nullpointer.moviescompose.models.TypeMovie
import com.nullpointer.moviescompose.models.apiResponse.MovieApiResponse
import kotlinx.coroutines.withTimeoutOrNull

class MoviesRemoteDataSourceImpl(
    private val moviesApiServices: MoviesApiServices,
    private val context: Context,
) : MoviesRemoteDataSource {

    private suspend fun callApiWithTimeout(
        timeout: Long = 3_000,
        callApi: suspend () -> MovieApiResponse,
    ): MovieApiResponse {
        if (!InternetCheck.isNetworkAvailable()) throw NetWorkException()
        return withTimeoutOrNull(timeout) { callApi() } ?: throw TimeOutException()
    }

    override suspend fun getUpComingMovies(): List<MovieDB> {
        val response = callApiWithTimeout {
            moviesApiServices.getUpcomingMovies(context.getString(R.string.language))
        }
        return response.results.map { MovieDB.fromMovieApi(it, TypeMovie.UP_COMING) }
    }

    override suspend fun getPopularMovies(): List<MovieDB> {
        val response = callApiWithTimeout {
            moviesApiServices.getPopularMovies(context.getString(R.string.language))
        }
        return response.results.map { MovieDB.fromMovieApi(it, TypeMovie.POPULAR) }
    }

    override suspend fun getToRateMovies(): List<MovieDB> {
        val response = callApiWithTimeout {
            moviesApiServices.getTopRatedMovies(context.getString(R.string.language))
        }
        return response.results.map { MovieDB.fromMovieApi(it, TypeMovie.TOP_RATED) }
    }

    override suspend fun getCreditsToMovie(idMovie: Long): List<Cast> {
        if (!InternetCheck.isNetworkAvailable()) throw NetWorkException()
        val response = withTimeoutOrNull(3_000) {
            moviesApiServices.getCredits(idMovie)
        } ?: throw TimeOutException()
        return response.cast.map(Cast::fromCastApi)
    }

    override suspend fun getMoviesForSearch(query: String): List<MovieApiResponse.Movie> {
        if (!InternetCheck.isNetworkAvailable()) throw NetWorkException()
        return withTimeoutOrNull(3_000) {
            moviesApiServices.getResultForSearch(
                language = context.getString(R.string.language),
                page = 1,
                includeAdult = true,
                query = query).results
        } ?: throw TimeOutException()
    }
}