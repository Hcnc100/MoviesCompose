package com.nullpointer.moviescompose.data.remote.apiServices

import com.nullpointer.moviescompose.BuildConfig
import com.nullpointer.moviescompose.models.MovieApiResponse
import org.intellij.lang.annotations.Language
import retrofit2.http.GET
import retrofit2.http.Query


interface MoviesApiServices {

    @GET("movie/popular?&api_key=${BuildConfig.API_KEY_MOVIES}")
    suspend fun getPopularMovies(
        @Query("language") language: String
    ): MovieApiResponse

    @GET("movie/top_rated?api_key=${BuildConfig.API_KEY_MOVIES}")
    suspend fun getTopRatedMovies(
        @Query("language") language: String
    ): MovieApiResponse

    @GET("movie/upcoming?api_key=${BuildConfig.API_KEY_MOVIES}")
    suspend fun getUpcomingMovies(
        @Query("language") language: String
    ): MovieApiResponse
}