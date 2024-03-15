package com.jnasser.movieapp.data.repositories

import com.jnasser.movieapp.data.datasource.RemoteVideoDataSource
import com.jnasser.movieapp.domain.response.ApiResponse
import com.jnasser.movieapp.domain.response.videos.VideoResponse

class VideosRepository(
    private val remoteVideoDataSource: RemoteVideoDataSource
) {

    suspend fun getVideos(id: Int): ApiResponse<List<VideoResponse>> =
        remoteVideoDataSource.getMovieVideos(id)

}