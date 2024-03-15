package com.jnasser.movieapp.domain.response.movie

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("success") val success: Boolean? = null,
    @SerializedName("status_code") val errorCode: Int? = null,
    @SerializedName("status_message") val message: String? = null,
    @SerializedName("original_title") val title: String? = null,
    @SerializedName("vote_average") val point: Double? = null,
    @SerializedName("genres") val genres: List<MovieGenre>? = null,
    @SerializedName("runtime") val time: Int? = null,
    @SerializedName("spoken_languages") val languages: List<MovieLanguage>? = null,
    @SerializedName("overview") val description: String? = null
)