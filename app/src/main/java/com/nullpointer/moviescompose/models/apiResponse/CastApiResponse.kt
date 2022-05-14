package com.nullpointer.moviescompose.models.apiResponse

data class CastApiResponse(
    val cast: List<CastApi>,
    val id: Int
) {
    data class CastApi(
        val name: String,
        val profile_path: String?,
        val gender:Int,
        val adult:Boolean
    )
}