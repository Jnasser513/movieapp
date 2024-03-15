package com.jnasser.movieapp.di

import com.jnasser.movieapp.data.datasource.LocalMovieDataSource
import com.jnasser.movieapp.data.datasource.RemoteMovieDataSource
import com.jnasser.movieapp.data.datasource.RemoteVideoDataSource
import com.jnasser.movieapp.data.repositories.MoviesRepository
import com.jnasser.movieapp.data.repositories.VideosRepository
import com.jnasser.movieapp.framework.requestmanager.APIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMoviesRepository(
        service: APIService,
        remoteMovieDataSource: RemoteMovieDataSource,
        localMovieDataSource: LocalMovieDataSource
    ): MoviesRepository {
        return MoviesRepository(service, remoteMovieDataSource, localMovieDataSource)
    }

    @Singleton
    @Provides
    fun provideVideosRepository(
        remoteVideoDataSource: RemoteVideoDataSource
    ): VideosRepository {
        return VideosRepository(remoteVideoDataSource)
    }

}