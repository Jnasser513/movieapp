package com.jnasser.movieapp.framework.requestmanager

import com.jnasser.movieapp.domain.response.GenericPagedResponse
import com.jnasser.movieapp.domain.response.movie.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET(APIConstants.API_BASE_URL + APIConstants.ENDPOINT_NOW_PLAYING)
    suspend fun getNowPlaying(@Path("page") page: Int): Response<GenericPagedResponse<MovieResponse>>

}