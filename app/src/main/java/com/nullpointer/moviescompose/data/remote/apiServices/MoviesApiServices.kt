package com.nullpointer.moviescompose.data.remote.apiServices

import com.nullpointer.moviescompose.BuildConfig
import com.nullpointer.moviescompose.models.apiResponse.cast.CastApiResponse
import com.nullpointer.moviescompose.models.apiResponse.MovieApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("movie/{idMovie}/credits?api_key=${BuildConfig.API_KEY_MOVIES}")
    suspend fun getCredits(
        @Path("idMovie") idMovie:Long
    ): CastApiResponse

    @GET("search/movie?api_key=${BuildConfig.API_KEY_MOVIES}")
    suspend fun getResultForSearch(
        @Query("language") language: String,
        @Query("page") page:Int,
        @Query("include_adult") includeAdult: Boolean,
        @Query("query") query: String,
    ):MovieApiResponse
}