package com.jnasser.movieapp.intereactors

import com.jnasser.movieapp.data.repositories.MoviesRepository

class GetNowPlayingMoviesUseCase(
    private val repository: MoviesRepository
) {

    fun invoke() = repository.getNowPlayingMovies()

}