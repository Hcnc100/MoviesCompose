package com.nullpointer.moviescompose.data.remote.apiServices

import com.nullpointer.moviescompose.BuildConfig
import com.nullpointer.moviescompose.models.MovieApiResponse
import retrofit2.http.GET


interface MoviesApiServices {

    @GET("movie/popular?api_key=${BuildConfig.API_KEY_MOVIES}")
    suspend fun getPopularMovies(): MovieApiResponse

    @GET("movie/top_rated?api_key=${BuildConfig.API_KEY_MOVIES}")
    suspend fun getTopRatedMovies(): MovieApiResponse

    @GET("movie/upcoming?api_key=${BuildConfig.API_KEY_MOVIES}")
    suspend fun getUpcomingMovies(): MovieApiResponse
}