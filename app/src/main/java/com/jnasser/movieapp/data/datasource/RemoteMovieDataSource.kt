package com.jnasser.movieapp.data.datasource

import com.jnasser.movieapp.domain.response.ApiResponse
import com.jnasser.movieapp.domain.response.movie.MovieCastResponse
import com.jnasser.movieapp.domain.response.movie.MovieDetailResponse

interface RemoteMovieDataSource {

    suspend fun getMovieDetail(id: Int): ApiResponse<MovieDetailResponse>

    suspend fun getMovieCast(id: Int): ApiResponse<MovieCastResponse>

}