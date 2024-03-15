package com.jnasser.movieapp.di

import com.jnasser.movieapp.data.repositories.MoviesRepository
import com.jnasser.movieapp.data.repositories.VideosRepository
import com.jnasser.movieapp.intereactors.GetMovieDetailUseCase
import com.jnasser.movieapp.intereactors.GetNowPlayingMoviesUseCase
import com.jnasser.movieapp.intereactors.GetVideosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideNowPlayingUseCase(
        repository: MoviesRepository
    ): GetNowPlayingMoviesUseCase {
        return GetNowPlayingMoviesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideVideosUseCase(
        repository: VideosRepository
    ): GetVideosUseCase {
        return GetVideosUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideMovieDetailUseCase(
        repository: MoviesRepository
    ): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(repository)
    }

}