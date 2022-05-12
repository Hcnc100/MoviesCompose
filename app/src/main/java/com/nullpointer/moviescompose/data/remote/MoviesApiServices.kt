package com.nullpointer.moviescompose.data.remote

import com.nullpointer.moviescompose.BuildConfig
import com.nullpointer.moviescompose.models.MovieApi
import retrofit2.http.GET


interface MoviesApiServices {

    @GET("movie/popular?api_key=${BuildConfig.API_KEY_MOVIES}")
    suspend fun getPopularMovies(): List<MovieApi>

    @GET("movie/top_rated?api_key=${BuildConfig.API_KEY_MOVIES}")
    suspend fun getTopRatedMovies(): List<MovieApi>

    @GET("/movie/upcoming?api_key=${BuildConfig.API_KEY_MOVIES}")
    suspend fun getUpcomingMovies(): List<MovieApi>
}