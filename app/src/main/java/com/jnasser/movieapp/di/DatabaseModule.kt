package com.jnasser.movieapp.di

import android.content.Context
import com.jnasser.movieapp.framework.databasemanager.MovieDB
import com.jnasser.movieapp.framework.databasemanager.daos.MovieDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieDB {
        return MovieDB.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideMovieDao(database: MovieDB): MovieDAO {
        return database.movieDao()
    }

}