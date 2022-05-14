package com.nullpointer.moviescompose.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nullpointer.moviescompose.core.constants.Constants.PREFIX_IMG_URL
import com.nullpointer.moviescompose.models.apiResponse.MovieApiResponse
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
@Entity(tableName = "table_movies")
data class MovieDB(
    val title: String,
    val originalTitle: String,
    val description: String,
    val releaseDate: Long?,
    val imgMovie: String,
    val imgCover: String,
    val typeMovie: TypeMovie,
    val voteAverage: Double,
    @PrimaryKey
    val id: Long? = null,
) : Parcelable {
    companion object {

        fun fromMovieApi(movieApi: MovieApiResponse.Movie, typeMovie: TypeMovie): MovieDB {
            val simpleFormat = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
            val date = simpleFormat.parse(movieApi.release_date)
            return MovieDB(
                title = movieApi.title,
                originalTitle = movieApi.original_title,
                imgCover = "$PREFIX_IMG_URL${movieApi.backdrop_path}",
                imgMovie = "$PREFIX_IMG_URL${movieApi.poster_path}",
                description = movieApi.overview,
                releaseDate = date?.time,
                voteAverage = movieApi.vote_average,
                typeMovie = typeMovie,
                id = movieApi.id
            )
        }

    }
}