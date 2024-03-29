package com.jnasser.movieapp.intereactors

import com.jnasser.movieapp.data.repositories.MoviesRepository


class GetLocalMoviesUseCase(
    private val repository: MoviesRepository
) {

    fun invoke() = repository.getLocalMovies()

}