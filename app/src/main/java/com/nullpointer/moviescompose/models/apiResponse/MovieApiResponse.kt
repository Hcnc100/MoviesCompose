package com.nullpointer.moviescompose.models.apiResponse

data class MovieApiResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
) {
    data class Movie(
        val backdrop_path: String,
        val id: Int,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: Double,
        val poster_path: String,
        val release_date: String,
        val title: String,
        val vote_average: Double
    )
}