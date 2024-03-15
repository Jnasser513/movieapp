package com.jnasser.movieapp.intereactors

import com.jnasser.movieapp.data.repositories.MoviesRepository

class NowPlayingMoviesUseCase(
    private val repository: MoviesRepository
) {

    fun invoke() = repository.getInventory()

}