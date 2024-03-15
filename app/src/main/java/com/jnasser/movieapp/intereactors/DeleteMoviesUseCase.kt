package com.jnasser.movieapp.intereactors

import com.jnasser.movieapp.data.repositories.MoviesRepository
import com.jnasser.movieapp.framework.databasemanager.entities.MovieEntity

class DeleteMoviesUseCase(
    private val repository: MoviesRepository
) {

    fun invoke(id: Int) = repository.deleteMovie(id)

}