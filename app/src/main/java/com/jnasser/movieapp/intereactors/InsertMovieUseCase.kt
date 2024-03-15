package com.jnasser.movieapp.intereactors

import com.jnasser.movieapp.data.repositories.MoviesRepository
import com.jnasser.movieapp.framework.databasemanager.entities.MovieEntity

class InsertMovieUseCase(
    private val repository: MoviesRepository
) {

    suspend fun invoke(movieEntity: MovieEntity) = repository.insertMovie(movieEntity)

}