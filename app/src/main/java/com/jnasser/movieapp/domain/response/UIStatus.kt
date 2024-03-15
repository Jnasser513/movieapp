package com.jnasser.movieapp.domain.response

sealed class UIStatus<T> {
    data class Success<T>(val data: T? = null): UIStatus<T>()
    data class Loading<T>(val message: String): UIStatus<T>()
    data class Error<T>(val exception: Exception): UIStatus<T>()
    data class ErrorWithMessage<T>(val message: String): UIStatus<T>()
    data class LogOut<T>(val message: String): UIStatus<T>()
    data class EmptyList<T>(val data: T? = null): UIStatus<T>()
}