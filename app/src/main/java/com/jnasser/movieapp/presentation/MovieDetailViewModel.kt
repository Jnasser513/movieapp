package com.jnasser.movieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jnasser.movieapp.di.IoDispatcher
import com.jnasser.movieapp.domain.response.ApiResponse
import com.jnasser.movieapp.domain.response.RoomResponse
import com.jnasser.movieapp.domain.response.UIStatus
import com.jnasser.movieapp.domain.response.movie.MovieCastResponse
import com.jnasser.movieapp.domain.response.movie.MovieDetailResponse
import com.jnasser.movieapp.domain.response.videos.VideoResponse
import com.jnasser.movieapp.framework.databasemanager.entities.MovieEntity
import com.jnasser.movieapp.intereactors.DeleteMoviesUseCase
import com.jnasser.movieapp.intereactors.GetLocalMoviesIdsUseCase
import com.jnasser.movieapp.intereactors.GetMovieCastUseCase
import com.jnasser.movieapp.intereactors.GetMovieDetailUseCase
import com.jnasser.movieapp.intereactors.GetVideosUseCase
import com.jnasser.movieapp.intereactors.InsertMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel
@Inject constructor(
    private val getVideosUseCase: GetVideosUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val insertMovieUseCase: InsertMovieUseCase,
    private val getLocalMoviesIdsUseCase: GetLocalMoviesIdsUseCase,
    private val deleteMovieUseCase: DeleteMoviesUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _statusVideos = MutableLiveData<UIStatus<List<VideoResponse>>>()
    val statusVideos: LiveData<UIStatus<List<VideoResponse>>> get() = _statusVideos

    fun getVideo(id: Int) {
        _statusVideos.value = UIStatus.Loading("Cargando...")
        viewModelScope.launch(ioDispatcher) {
            _statusVideos.postValue(
                when (val response = getVideosUseCase.invoke(id)) {
                    is ApiResponse.Error -> UIStatus.Error(response.exception)
                    is ApiResponse.ErrorWithMessage -> UIStatus.ErrorWithMessage(response.message)
                    is ApiResponse.Success -> UIStatus.Success(response.data)
                    is ApiResponse.LogOut -> UIStatus.LogOut(response.message)
                    is ApiResponse.EmptyList -> UIStatus.EmptyList(response.data)
                }
            )
        }
    }

    private val _statusDetail = MutableLiveData<UIStatus<MovieDetailResponse>>()
    val statusDetail: LiveData<UIStatus<MovieDetailResponse>> get() = _statusDetail

    fun getMovieDetail(id: Int) {
        _statusDetail.value = UIStatus.Loading("Cargando...")
        viewModelScope.launch(ioDispatcher) {
            _statusDetail.postValue(
                when (val response = getMovieDetailUseCase.invoke(id)) {
                    is ApiResponse.Error -> UIStatus.Error(response.exception)
                    is ApiResponse.ErrorWithMessage -> UIStatus.ErrorWithMessage(response.message)
                    is ApiResponse.Success -> UIStatus.Success(response.data)
                    is ApiResponse.LogOut -> UIStatus.LogOut(response.message)
                    is ApiResponse.EmptyList -> UIStatus.EmptyList(response.data)
                }
            )
        }
    }

    private val _statusCast = MutableLiveData<UIStatus<MovieCastResponse>>()
    val statusCast: LiveData<UIStatus<MovieCastResponse>> get() = _statusCast

    fun getMovieCast(id: Int) {
        _statusCast.value = UIStatus.Loading("Cargando...")
        viewModelScope.launch(ioDispatcher) {
            _statusCast.postValue(
                when (val response = getMovieCastUseCase.invoke(id)) {
                    is ApiResponse.Error -> UIStatus.Error(response.exception)
                    is ApiResponse.ErrorWithMessage -> UIStatus.ErrorWithMessage(response.message)
                    is ApiResponse.Success -> UIStatus.Success(response.data)
                    is ApiResponse.LogOut -> UIStatus.LogOut(response.message)
                    is ApiResponse.EmptyList -> UIStatus.EmptyList(response.data)
                }
            )
        }
    }

    private val _statusLocalMoviesIds = MutableLiveData<UIStatus<Int>>()
    val statusLocalMoviesIds: LiveData<UIStatus<Int>> get() = _statusLocalMoviesIds

    fun getLocalMoviesIds(id: Int) {
        _statusLocalMoviesIds.value = UIStatus.Loading("Cargando...")
        viewModelScope.launch(ioDispatcher) {
            _statusLocalMoviesIds.postValue(
                when (val response = getLocalMoviesIdsUseCase.invoke(id)) {
                    is RoomResponse.Error -> UIStatus.Error(response.exception)
                    is RoomResponse.ErrorWithMessage -> UIStatus.ErrorWithMessage(response.message)
                    is RoomResponse.Success -> UIStatus.Success(response.data)
                    is RoomResponse.EmptyList -> UIStatus.EmptyList(response.data)
                }
            )
        }
    }

    private val _statusInsertMovie = MutableLiveData<UIStatus<Long>>()
    val statusInsertMovie: LiveData<UIStatus<Long>> get() = _statusInsertMovie

    fun insertMovie(movie: MovieEntity)  {
        _statusInsertMovie.value = UIStatus.Loading("Cargando...")
        viewModelScope.launch(Dispatchers.IO) {
            _statusInsertMovie.postValue(
                when (val response = insertMovieUseCase.invoke(movie)) {
                    is RoomResponse.Error -> UIStatus.Error(response.exception)
                    is RoomResponse.ErrorWithMessage -> UIStatus.ErrorWithMessage(response.message)
                    is RoomResponse.Success -> UIStatus.Success()
                    else -> UIStatus.ErrorWithMessage("Error insertando pelicula")
                }
            )
        }
    }

    private val _statusDeleteMovie = MutableLiveData<UIStatus<Int>>()
    val statusDeleteMovie: LiveData<UIStatus<Int>> get() = _statusDeleteMovie

    fun deleteMovie(id: Int)  {
        _statusDeleteMovie.value = UIStatus.Loading("Cargando...")
        viewModelScope.launch(Dispatchers.IO) {
            _statusDeleteMovie.postValue(
                when (val response = deleteMovieUseCase.invoke(id)) {
                    is RoomResponse.Error -> UIStatus.Error(response.exception)
                    is RoomResponse.ErrorWithMessage -> UIStatus.ErrorWithMessage(response.message)
                    is RoomResponse.Success -> UIStatus.Success()
                    else -> UIStatus.ErrorWithMessage("No se pudo eliminar de favoritos")
                }
            )
        }
    }

}