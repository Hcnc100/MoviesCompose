package com.nullpointer.moviescompose.models.apiResponse

import com.nullpointer.moviescompose.models.apiResponse.movie.MovieDTO

data class MovieApiResponse(
    val page: Int,
    val results: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
)