package com.jnasser.movieapp.domain.response.movie

import com.google.gson.annotations.SerializedName

data class MovieCast(
    @SerializedName("profile_path") val image: String? = null,
    @SerializedName("name") val name: String? = null
)