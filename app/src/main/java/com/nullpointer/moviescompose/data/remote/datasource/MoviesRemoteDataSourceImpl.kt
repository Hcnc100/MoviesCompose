package com.nullpointer.moviescompose.data.remote.datasource

import android.content.Context
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.core.constants.Constants
import com.nullpointer.moviescompose.core.utils.ExceptionManager
import com.nullpointer.moviescompose.core.utils.InternetCheck
import com.nullpointer.moviescompose.data.remote.apiServices.MoviesApiServices
import com.nullpointer.moviescompose.models.Cast
import com.nullpointer.moviescompose.models.MovieDB
import com.nullpointer.moviescompose.models.TypeMovie
import com.nullpointer.moviescompose.models.apiResponse.movie.MovieDTO
import kotlinx.coroutines.withTimeoutOrNull
import java.text.SimpleDateFormat
import java.util.*

class MoviesRemoteDataSourceImpl(
    private val moviesApiServices: MoviesApiServices,
    private val context: Context,
) : MoviesRemoteDataSource {
    override suspend fun getUpComingMovies(): List<MovieDB> {
        val response = callApiWithTimeout {
            moviesApiServices.getUpcomingMovies(context.getString(R.string.language))
        }
        return response.results.map { it.toMovie(TypeMovie.UP_COMING) }
    }

    override suspend fun getPopularMovies(): List<MovieDB> {
        val response = callApiWithTimeout {
            moviesApiServices.getPopularMovies(context.getString(R.string.language))
        }
        return response.results.map { it.toMovie(TypeMovie.POPULAR) }
    }

    override suspend fun getToRateMovies(): List<MovieDB> {
        val response = callApiWithTimeout {
            moviesApiServices.getTopRatedMovies(context.getString(R.string.language))
        }
        return response.results.map { it.toMovie(TypeMovie.TOP_RATED) }
    }

    override suspend fun getCreditsToMovie(idMovie: Long): List<Cast> {
        val response = callApiWithTimeout {
            moviesApiServices.getCredits(idMovie)
        }
        return response.cast.map(Cast::fromCastApi)
    }

    private suspend fun <T> callApiWithTimeout(
        timeout: Long = 3_000,
        callApi: suspend () -> T,
    ): T {
        if (!InternetCheck.isNetworkAvailable()) throw Exception(ExceptionManager.NO_NETWORK_ERROR)
        return withTimeoutOrNull(timeout) { callApi() }!!
    }


    override suspend fun getMoviesForSearch(query: String): List<MovieDTO> {
        return callApiWithTimeout {
            moviesApiServices.getResultForSearch(
                language = context.getString(R.string.language),
                page = 1,
                includeAdult = true,
                query = query
            ).results
        }
    }

}

fun MovieDTO.toMovie(typeMovie: TypeMovie): MovieDB {
    val simpleFormat = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
    val date = releaseDate?.let { simpleFormat.parse(it) }
    return MovieDB(
        title = title,
        originalTitle = originalTitle,
        imgCover = "${Constants.PREFIX_IMG_URL}${backdropPath}",
        imgMovie = "${Constants.PREFIX_IMG_URL}${posterPath}",
        description = overview,
        releaseDate = date?.time,
        voteAverage = voteAverage,
        typeMovie = typeMovie,
        id = id
    )

}