package com.jnasser.movieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jnasser.movieapp.di.IoDispatcher
import com.jnasser.movieapp.domain.response.ApiResponse
import com.jnasser.movieapp.domain.response.RoomResponse
import com.jnasser.movieapp.domain.response.UIStatus
import com.jnasser.movieapp.domain.response.movie.MovieResponse
import com.jnasser.movieapp.intereactors.GetNowPlayingMoviesUseCase
import com.jnasser.movieapp.intereactors.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _statusNowPlayingMovies = MutableLiveData<UIStatus<List<MovieResponse>>>()
    val statusNowPlayingMovies: LiveData<UIStatus<List<MovieResponse>>> get() = _statusNowPlayingMovies

    fun getNowPlayingMovies() {
        _statusNowPlayingMovies.value = UIStatus.Loading("Cargando...")
        viewModelScope.launch(ioDispatcher) {
            _statusNowPlayingMovies.postValue(
                when (val response = getNowPlayingMoviesUseCase.invokeFirst()) {
                    is ApiResponse.Error -> UIStatus.Error(response.exception)
                    is ApiResponse.ErrorWithMessage -> UIStatus.ErrorWithMessage(response.message)
                    is ApiResponse.Success -> UIStatus.Success(response.data)
                    is ApiResponse.EmptyList -> UIStatus.EmptyList(response.data)
                    is ApiResponse.LogOut -> UIStatus.LogOut(response.message)
                }
            )
        }
    }

    fun getNowPLayingMovies(): LiveData<PagingData<MovieResponse>> {
        return getNowPlayingMoviesUseCase.invoke().cachedIn(viewModelScope)
    }

    fun getPopularMovies(): LiveData<PagingData<MovieResponse>> {
        return getPopularMoviesUseCase.invoke().cachedIn(viewModelScope)
    }

}