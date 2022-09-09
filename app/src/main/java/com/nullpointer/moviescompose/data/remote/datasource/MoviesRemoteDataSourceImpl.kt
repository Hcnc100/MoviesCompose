package com.nullpointer.moviescompose.data.remote.datasource

import android.content.Context
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.core.utils.ExceptionManager
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

    private suspend fun <T> callApiWithTimeout(
        timeout: Long = 3_000,
        callApi: suspend () -> T,
    ): T {
        if (!InternetCheck.isNetworkAvailable()) throw Exception(ExceptionManager.NO_NETWORK_ERROR)
        return withTimeoutOrNull(timeout) { callApi() }!!
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
        val response = callApiWithTimeout {
            moviesApiServices.getCredits(idMovie)
        }
        return response.cast.map(Cast::fromCastApi)
    }

    override suspend fun getMoviesForSearch(query: String): List<MovieApiResponse.Movie> {
        return callApiWithTimeout {
            moviesApiServices.getResultForSearch(
                language = context.getString(R.string.language),
                page = 1,
                includeAdult = true,
                query = query).results
        }
    }
}