package com.nullpointer.moviescompose.models

import android.os.Parcelable
import com.nullpointer.moviescompose.core.constants.Constants
import com.nullpointer.moviescompose.models.apiResponse.CastApiResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cast(
    val name: String,
    val urlImg: String? = null,
) : Parcelable {
    companion object {
        fun fromCastApi(castApi: CastApiResponse.CastApi): Cast {
            val urlImg = castApi.profile_path?.let {
                "${Constants.PREFIX_IMG_URL}$it"
            }
            return Cast(
                name = castApi.name,
                urlImg = urlImg
            )
        }
    }
}