package com.jnasser.movieapp.intereactors

import com.jnasser.movieapp.data.repositories.MoviesRepository
import com.jnasser.movieapp.data.repositories.VideosRepository

class GetMovieCastUseCase(
    private val repository: MoviesRepository
) {

    suspend fun invoke(id: Int) = repository.getMovieCast(id)

}