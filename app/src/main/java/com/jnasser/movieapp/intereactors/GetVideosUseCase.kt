package com.jnasser.movieapp.intereactors

import com.jnasser.movieapp.data.repositories.VideosRepository

class GetVideosUseCase(
    private val repository: VideosRepository
) {

    suspend fun invoke(id: Int) = repository.getVideos(id)

}