package com.jnasser.movieapp.domain.response.movie

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("poster_path") val image: String,
    @SerializedName("id") val id: Int,
    @SerializedName("original_title") val title: String,
    @SerializedName("vote_average") val points: Double
)