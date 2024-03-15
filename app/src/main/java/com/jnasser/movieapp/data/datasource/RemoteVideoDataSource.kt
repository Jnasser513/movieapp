package com.jnasser.movieapp.data.datasource

import com.jnasser.movieapp.domain.response.ApiResponse
import com.jnasser.movieapp.domain.response.videos.VideoResponse

interface RemoteVideoDataSource {

    suspend fun getMovieVideos(id: Int): ApiResponse<List<VideoResponse>>

}