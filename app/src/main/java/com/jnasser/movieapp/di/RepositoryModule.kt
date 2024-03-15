package com.jnasser.movieapp.di

import com.jnasser.movieapp.data.repositories.MoviesRepository
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
        service: APIService
    ): MoviesRepository {
        return MoviesRepository(service)
    }

}