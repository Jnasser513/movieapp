package com.jnasser.movieapp.di

import com.jnasser.movieapp.data.datasource.RemoteMovieDataSource
import com.jnasser.movieapp.data.datasource.RemoteVideoDataSource
import com.jnasser.movieapp.framework.requestmanager.APIService
import com.jnasser.movieapp.framework.requestmanager.datasource.MovieRetrofitDataSource
import com.jnasser.movieapp.framework.requestmanager.datasource.VideosRetrofitDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideRemoteVideosDataSource(
        service: APIService
    ): RemoteVideoDataSource {
        return VideosRetrofitDataSource(service)
    }

    @Singleton
    @Provides
    fun provideRemoteMoviesDataSource(
        service: APIService
    ): RemoteMovieDataSource {
        return MovieRetrofitDataSource(service)
    }

}