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
    @PrimaryKey(autoGenerate = true)
    val id: Long=0,
) : Parcelable