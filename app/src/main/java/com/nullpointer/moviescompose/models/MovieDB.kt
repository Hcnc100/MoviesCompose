package com.nullpointer.moviescompose.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_movies")
data class MovieDB(
    val title: String,
    val imgMovie: String,
    val imgCover: String,
    val typeMovie: TypeMovie,
    @PrimaryKey
    val id:Long?=null
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