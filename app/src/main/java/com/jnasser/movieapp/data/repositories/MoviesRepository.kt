package com.jnasser.movieapp.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.jnasser.movieapp.data.datasource.LocalMovieDataSource
import com.jnasser.movieapp.data.datasource.RemoteMovieDataSource
import com.jnasser.movieapp.domain.response.RoomResponse
import com.jnasser.movieapp.framework.databasemanager.entities.MovieEntity
import com.jnasser.movieapp.framework.requestmanager.APIService
import com.jnasser.movieapp.framework.requestmanager.pagingDataSource.NowPlayingMoviesPagingSource
import com.jnasser.movieapp.framework.requestmanager.pagingDataSource.PopularMoviesPagingSource

class MoviesRepository(
    private val service: APIService,
    private val remoteMovieDataSource: RemoteMovieDataSource,
    private val localMovieDataSource: LocalMovieDataSource
) {

    suspend fun getFirstNowPlayingMovies() =
        remoteMovieDataSource.getNowPlayingMovies()
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

    fun getPopularMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 40
            ),
            pagingSourceFactory = {
                PopularMoviesPagingSource(service)
            }, initialKey = 1
        ).liveData

    suspend fun getMovieDetail(id: Int) =
        remoteMovieDataSource.getMovieDetail(id)

    suspend fun getMovieCast(id: Int) =
        remoteMovieDataSource.getMovieCast(id)

    suspend fun insertMovie(movieEntity: MovieEntity): RoomResponse<Long> = localMovieDataSource.insertMovie(movieEntity)

    fun getLocalMovies(): RoomResponse<List<MovieEntity>> = localMovieDataSource.getMovies()

    fun getLocalMoviesIds(id: Int): RoomResponse<Int> = localMovieDataSource.getMoviesIds(id)

    fun deleteMovie(id: Int): RoomResponse<Int> = localMovieDataSource.deleteMovie(id)

}