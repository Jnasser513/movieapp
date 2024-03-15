package com.jnasser.movieapp.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.jnasser.movieapp.framework.requestmanager.APIConstants
import com.jnasser.movieapp.framework.requestmanager.APIService
import com.jnasser.movieapp.ui.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun getToken(): String {
        return "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiYzhmZjA3MDc4ZTY3MGRmYjcwMWYwNGMxNzlmNzU3YiIsInN1YiI6IjY1ZjMyNGVlZDY0YWMyMDE4NzYxZTBmMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.WcT6WOTCft4tozCS-S5S7NbpLbmg_15WCt7zyeFhBGg"
    }

    @Singleton
    @Provides
    fun provideAPIService(): APIService {
        return Retrofit.Builder()
            .baseUrl(APIConstants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor { chain ->
                        val token = getToken()
                        chain.proceed(
                            chain
                                .request()
                                .newBuilder()
                                .addHeader("Authorization", "Bearer $token")
                                .addHeader("accept", "application/json")
                                .build()
                        )
                    }
                    .addInterceptor(interceptor)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .callTimeout(1, TimeUnit.MINUTES)
                    .build()
            )
            .build()
            .create(APIService::class.java)
    }

}