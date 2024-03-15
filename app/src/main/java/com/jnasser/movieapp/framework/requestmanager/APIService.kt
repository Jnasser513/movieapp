package com.jnasser.movieapp.framework.requestmanager

import com.jnasser.movieapp.domain.response.GenericPagedResponse
import com.jnasser.movieapp.domain.response.movie.MovieCastResponse
import com.jnasser.movieapp.domain.response.movie.MovieDetailResponse
import com.jnasser.movieapp.domain.response.movie.MovieResponse
import com.jnasser.movieapp.domain.response.videos.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET(APIConstants.API_BASE_URL + APIConstants.ENDPOINT_NOW_PLAYING)
    suspend fun getNowPlaying(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<GenericPagedResponse<MovieResponse>>

    @GET(APIConstants.API_BASE_URL + APIConstants.ENDPOINT_MOVIE_VIDEOS)
    suspend fun getVideos(@Path("movieId") id: Int): Response<GenericPagedResponse<VideoResponse>>

    @GET(APIConstants.API_BASE_URL + APIConstants.ENDPOINT_MOVIE_DETAIL)
    suspend fun getMovieDetail(
        @Path("movieId") id: Int,
        @Query("language") language: String
    ): Response<MovieDetailResponse>

    @GET(APIConstants.API_BASE_URL + APIConstants.ENDPOINT_MOVIE_CAST)
    suspend fun getMovieCast(
        @Path("movieId") id: Int,
        @Query("language") language: String
    ): Response<MovieCastResponse>

    @GET(APIConstants.API_BASE_URL + APIConstants.ENDPOINT_POPULAR_MOVIES)
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<GenericPagedResponse<MovieResponse>>

}