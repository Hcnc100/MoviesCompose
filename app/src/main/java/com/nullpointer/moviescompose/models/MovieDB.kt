package com.nullpointer.moviescompose.models

import androidx.room.Entity

@Entity(tableName = "table_movies")
data class MovieDB(
    val title: String,
    val imgMovie: String,
    val imgCover: String,
    val typeMovie: TypeMovie,
) {
    companion object {
        fun fromMovieApi(movieApi: MovieApi, typeMovie: TypeMovie): MovieDB =
            MovieDB(
                title = movieApi.name,
                imgCover = movieApi.urlImg,
                imgMovie = movieApi.urlImg,
                typeMovie = typeMovie
            )
    }
}