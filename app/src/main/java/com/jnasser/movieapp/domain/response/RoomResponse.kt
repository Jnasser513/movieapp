package com.jnasser.movieapp.domain.response

sealed class RoomResponse<T> {
    data class Success<T>(val data: T? = null): RoomResponse<T>()
    data class Error<T>(val exception: Exception): RoomResponse<T>()
    data class ErrorWithMessage<T>(val message: String): RoomResponse<T>()
    data class EmptyList<T>(val data: T): RoomResponse<T>()
}