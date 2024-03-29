package com.jnasser.movieapp.data.datasource

import com.jnasser.movieapp.domain.response.RoomResponse
import com.jnasser.movieapp.framework.databasemanager.entities.MovieEntity

interface LocalMovieDataSource {

    suspend fun insertMovie(movieEntity: MovieEntity): RoomResponse<Long>

    fun getMovies(): RoomResponse<List<MovieEntity>>

    fun getMoviesIds(id: Int): RoomResponse<Int>

    fun deleteMovie(id: Int): RoomResponse<Int>

}