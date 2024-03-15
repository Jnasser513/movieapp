package com.jnasser.movieapp.domain.response.movie

import com.google.gson.annotations.SerializedName

data class MovieCastResponse(
    @SerializedName("success") val success: Boolean? = null,
    @SerializedName("status_code") val errorCode: Int? = null,
    @SerializedName("status_message") val message: String? = null,
    @SerializedName("cast") val cast: List<MovieCast>? = null
)
