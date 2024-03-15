package com.jnasser.movieapp.di

import com.jnasser.movieapp.data.repositories.MoviesRepository
import com.jnasser.movieapp.data.repositories.VideosRepository
import com.jnasser.movieapp.intereactors.DeleteMoviesUseCase
import com.jnasser.movieapp.intereactors.GetLocalMoviesIdsUseCase
import com.jnasser.movieapp.intereactors.GetLocalMoviesUseCase
import com.jnasser.movieapp.intereactors.GetMovieCastUseCase
import com.jnasser.movieapp.intereactors.GetMovieDetailUseCase
import com.jnasser.movieapp.intereactors.GetNowPlayingMoviesUseCase
import com.jnasser.movieapp.intereactors.GetVideosUseCase
import com.jnasser.movieapp.intereactors.InsertMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideNowPlayingUseCase(
        repository: MoviesRepository
    ): GetNowPlayingMoviesUseCase {
        return GetNowPlayingMoviesUseCase(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideVideosUseCase(
        repository: VideosRepository
    ): GetVideosUseCase {
        return GetVideosUseCase(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideMovieDetailUseCase(
        repository: MoviesRepository
    ): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideMovieCastUseCase(
        repository: MoviesRepository
    ): GetMovieCastUseCase {
        return GetMovieCastUseCase(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideInsertMovie(
        movieRepository: MoviesRepository
    ) = InsertMovieUseCase(movieRepository)

    @ViewModelScoped
    @Provides
    fun provideGetMovie(
        movieRepository: MoviesRepository
    ) = GetLocalMoviesUseCase(movieRepository)

    @ViewModelScoped
    @Provides
    fun provideDeleteMovie(
        movieRepository: MoviesRepository
    ) = DeleteMoviesUseCase(movieRepository)

    @ViewModelScoped
    @Provides
    fun provideGetMoviesIds(
        movieRepository: MoviesRepository
    ) = GetLocalMoviesIdsUseCase(movieRepository)

}