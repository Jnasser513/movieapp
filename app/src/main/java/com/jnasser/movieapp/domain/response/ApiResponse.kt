package com.jnasser.movieapp.domain.response

sealed class ApiResponse<T> {
    data class Success<T>(val data: T? = null): ApiResponse<T>()
    data class Error<T>(val exception: Exception): ApiResponse<T>()
    data class ErrorWithMessage<T>(val message: String): ApiResponse<T>()
    data class LogOut<T>(val message: String): ApiResponse<T>()
    data class EmptyList<T>(val data: T? = null): ApiResponse<T>()
}