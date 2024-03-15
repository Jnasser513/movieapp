package com.jnasser.movieapp.framework.databasemanager.datasource

import com.jnasser.movieapp.data.datasource.LocalMovieDataSource
import com.jnasser.movieapp.domain.response.RoomResponse
import com.jnasser.movieapp.framework.databasemanager.daos.MovieDAO
import com.jnasser.movieapp.framework.databasemanager.entities.MovieEntity
import okio.IOException

class MovieRoomDataSource(
    private val movieDao: MovieDAO
) : LocalMovieDataSource {

    override suspend fun insertMovie(movieEntity: MovieEntity): RoomResponse<Long> {
        return try {
            val insertedRow = movieDao.insertMovie(movieEntity)
            if (insertedRow != -1L) {
                RoomResponse.Success(insertedRow)
            } else {
                RoomResponse.ErrorWithMessage("No se pudo insertar la pelicula")
            }
        } catch (e: IOException) {
            RoomResponse.Error(e)
        }
    }

    override fun getMovies(): RoomResponse<List<MovieEntity>> {
        return try {
            val movies = movieDao.getMovies()
            if (movies.isEmpty()) {
                RoomResponse.EmptyList(movies)
            } else {
                RoomResponse.Success(movies)
            }
        } catch (e: IOException) {
            RoomResponse.Error(e)
        }
    }

    override fun getMoviesIds(id: Int): RoomResponse<Int> {
        return try {
            val count = movieDao.getMoviesIds(id)
            if (count > 0) {
                RoomResponse.Success()
            } else {
                RoomResponse.EmptyList(count)
            }
        } catch (e: IOException) {
            RoomResponse.Error(e)
        }
    }

    override fun deleteMovie(id: Int): RoomResponse<Int> {
        return try {
            val deletedMovie = movieDao.deleteMovie(id)
            if (deletedMovie != -1) {
                RoomResponse.Success()
            } else {
                RoomResponse.ErrorWithMessage("No se pudo eliminar la pelicula")
            }
        } catch (e: IOException) {
            RoomResponse.Error(e)
        }
    }
}