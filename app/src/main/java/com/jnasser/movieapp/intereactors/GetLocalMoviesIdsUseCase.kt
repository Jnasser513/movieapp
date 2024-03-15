package com.jnasser.movieapp.intereactors

import com.jnasser.movieapp.data.repositories.MoviesRepository


class GetLocalMoviesIdsUseCase(
    private val repository: MoviesRepository
) {

    fun invoke(id: Int) = repository.getLocalMoviesIds(id)

}