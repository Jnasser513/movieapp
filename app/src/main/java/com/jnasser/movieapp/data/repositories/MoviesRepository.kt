package com.jnasser.movieapp.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.jnasser.movieapp.data.datasource.RemoteMovieDataSource
import com.jnasser.movieapp.framework.requestmanager.APIService
import com.jnasser.movieapp.framework.requestmanager.pagingDataSource.NowPlayingMoviesPagingSource

class MoviesRepository(
    private val service: APIService,
    private val remoteMovieDataSource: RemoteMovieDataSource
) {

    fun getNowPlayingMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 40
            ),
            pagingSourceFactory = {
                NowPlayingMoviesPagingSource(service)
            }, initialKey = 1
        ).liveData

    suspend fun getMovieDetail(id: Int) =
        remoteMovieDataSource.getMovieDetail(id)

    suspend fun getMovieCast(id: Int) =
        remoteMovieDataSource.getMovieCast(id)

}