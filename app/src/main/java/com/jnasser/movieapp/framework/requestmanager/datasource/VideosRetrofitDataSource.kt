package com.jnasser.movieapp.framework.requestmanager.datasource

import com.jnasser.movieapp.data.datasource.RemoteVideoDataSource
import com.jnasser.movieapp.domain.response.ApiResponse
import com.jnasser.movieapp.domain.response.videos.VideoResponse
import com.jnasser.movieapp.framework.requestmanager.APIService
import okio.IOException

class VideosRetrofitDataSource(
    private val service: APIService
): RemoteVideoDataSource {

    override suspend fun getMovieVideos(id: Int): ApiResponse<List<VideoResponse>> {
        return try {
            val call = service.getVideos(id)
            val response = call.body()
            val message = response?.message ?: "Error al procesar la solicitud"
            val data = response?.results ?: emptyList<VideoResponse>()

            if(call.isSuccessful) {
                if (response?.success == false) {
                    ApiResponse.ErrorWithMessage(message)
                } else {
                    if (data.isEmpty()) {
                        ApiResponse.EmptyList(data)
                    } else {
                        ApiResponse.Success(data)
                    }
                }
            } else {
                when(call.code()) {
                    401 -> ApiResponse.LogOut("Sesion caducada")
                    500 -> ApiResponse.ErrorWithMessage("Error de servidor")
                    else -> ApiResponse.ErrorWithMessage(message)
                }
            }
        } catch (e: IOException) {
            ApiResponse.Error(e)
        }
    }
}