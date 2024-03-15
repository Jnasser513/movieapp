package com.jnasser.movieapp.domain.response.videos

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("key") val key: String,
    @SerializedName("type") val type: String
)
