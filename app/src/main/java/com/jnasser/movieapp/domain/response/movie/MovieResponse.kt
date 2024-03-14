package com.jnasser.movieapp.domain.response.movie

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("backdrop_path") val image: String,
    @SerializedName("original_title") val title: String,
    @SerializedName("vote_average") val qualification: Double
)