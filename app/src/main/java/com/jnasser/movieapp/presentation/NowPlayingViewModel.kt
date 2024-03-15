package com.jnasser.movieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jnasser.movieapp.di.IoDispatcher
import com.jnasser.movieapp.domain.response.movie.MovieResponse
import com.jnasser.movieapp.intereactors.GetNowPlayingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel
@Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    fun getNowPLayingMovies(): LiveData<PagingData<MovieResponse>> {
        return getNowPlayingMoviesUseCase.invoke().cachedIn(viewModelScope)
    }

}