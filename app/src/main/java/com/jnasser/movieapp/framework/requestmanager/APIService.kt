package com.jnasser.movieapp.framework.requestmanager

import com.jnasser.movieapp.domain.response.GenericPagedResponse
import com.jnasser.movieapp.domain.response.movie.MovieResponse
import com.jnasser.movieapp.domain.response.videos.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET(APIConstants.API_BASE_URL + APIConstants.ENDPOINT_NOW_PLAYING)
    suspend fun getNowPlaying(@Query("language") language: String, @Query("page") page: Int): Response<GenericPagedResponse<MovieResponse>>

    @GET(APIConstants.API_BASE_URL + APIConstants.ENDPOINT_MOVIE_VIDEOS)
    suspend fun getVideos(@Path("movieId") id: Int): Response<GenericPagedResponse<VideoResponse>>

}