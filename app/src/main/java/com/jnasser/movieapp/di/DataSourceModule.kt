package com.jnasser.movieapp.di

import com.jnasser.movieapp.framework.requestmanager.APIService
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
    ): VideosRetrofitDataSource {
        return VideosRetrofitDataSource(service)
    }

}