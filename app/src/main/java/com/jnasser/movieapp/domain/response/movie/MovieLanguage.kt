package com.jnasser.movieapp.domain.response.movie

import com.google.gson.annotations.SerializedName

data class MovieLanguage(
    @SerializedName("name") val name: String
)
