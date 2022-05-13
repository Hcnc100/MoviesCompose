package com.nullpointer.moviescompose.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_movies")
data class MovieDB(
    val title: String,
    val originalTitle:String,
    val description:String,
    val releaseDate:String,
    val imgMovie: String,
    val imgCover: String,
    val typeMovie: TypeMovie,
    val voteAverage:Double,
    @PrimaryKey
    val id:Long?=null
) {
    companion object {
        private const val PREFIX_IMG="https://image.tmdb.org/t/p/w500"

        fun fromMovieApi(movieApi: MovieApiResponse.Movie, typeMovie: TypeMovie): MovieDB =
            MovieDB(
                title = movieApi.title,
                originalTitle = movieApi.original_title,
                imgCover = "$PREFIX_IMG${movieApi.backdrop_path}",
                imgMovie = "$PREFIX_IMG${movieApi.poster_path}",
                description = movieApi.overview,
                releaseDate = movieApi.release_date,
                voteAverage = movieApi.vote_average,
                typeMovie = typeMovie
            )
    }
}