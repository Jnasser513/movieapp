package com.jnasser.movieapp.domain.response

import com.google.gson.annotations.SerializedName

data class GenericPagedResponse<T>(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("status_code") val errorCode: Int?,
    @SerializedName("status_message") val message: String?,
    @SerializedName("page") val page: Int?,
    @SerializedName("results") val results: List<T>?,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("total_results") val totalResults: Int?
)