package com.jnasser.movieapp.domain.response.movie

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("original_title") val title: String,
    @SerializedName("vote_average") val point: Double,
    @SerializedName("genres") val genres: MovieGenre,
    @SerializedName("runtime") val time: Int,
    @SerializedName("spoken_languages") val languages: MovieLanguage,
    @SerializedName("overview") val description: String,

)