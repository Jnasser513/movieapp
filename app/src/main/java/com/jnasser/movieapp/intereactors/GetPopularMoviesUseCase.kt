package com.jnasser.movieapp.intereactors

import com.jnasser.movieapp.data.repositories.MoviesRepository

class GetPopularMoviesUseCase(
    private val repository: MoviesRepository
) {

    fun invoke() = repository.getPopularMovies()

}