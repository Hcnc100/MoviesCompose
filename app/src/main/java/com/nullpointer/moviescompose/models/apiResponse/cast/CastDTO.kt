package com.nullpointer.moviescompose.models.apiResponse.cast

data class CastDTO(
    val name: String,
    val profile_path: String?,
    val gender:Int,
    val adult:Boolean
)