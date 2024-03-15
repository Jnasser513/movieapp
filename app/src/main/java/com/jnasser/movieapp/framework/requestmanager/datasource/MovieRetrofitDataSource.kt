package com.jnasser.movieapp.framework.requestmanager.datasource

import com.jnasser.movieapp.data.datasource.RemoteMovieDataSource
import com.jnasser.movieapp.data.datasource.RemoteVideoDataSource
import com.jnasser.movieapp.domain.response.ApiResponse
import com.jnasser.movieapp.domain.response.movie.MovieCastResponse
import com.jnasser.movieapp.domain.response.movie.MovieDetailResponse
import com.jnasser.movieapp.domain.response.videos.VideoResponse
import com.jnasser.movieapp.framework.requestmanager.APIConstants
import com.jnasser.movieapp.framework.requestmanager.APIService
import okio.IOException

class MovieRetrofitDataSource(
    private val service: APIService
) : RemoteMovieDataSource {

    override suspend fun getMovieDetail(id: Int): ApiResponse<MovieDetailResponse> {
        return try {
            val call = service.getMovieDetail(id, APIConstants.LANGUAGE)
            val response = call.body()
            val message = response?.message ?: "Error al procesar la solicitud"

            if (call.isSuccessful) {
                if (response?.success == false) {
                    ApiResponse.ErrorWithMessage(message)
                } else {
                    ApiResponse.Success(response)
                }
            } else {
                when (call.code()) {
                    401 -> ApiResponse.LogOut("Sesion caducada")
                    500 -> ApiResponse.ErrorWithMessage("Error de servidor")
                    else -> ApiResponse.ErrorWithMessage(message)
                }
            }
        } catch (e: IOException) {
            ApiResponse.Error(e)
        }
    }

    override suspend fun getMovieCast(id: Int): ApiResponse<MovieCastResponse> {
        return try {
            val call = service.getMovieCast(id, APIConstants.LANGUAGE)
            val response = call.body()
            val message = response?.message ?: "Error al procesar la solicitud"

            if (call.isSuccessful) {
                if (response?.success == false) {
                    ApiResponse.ErrorWithMessage(message)
                } else {
                    ApiResponse.Success(response)
                }
            } else {
                when (call.code()) {
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