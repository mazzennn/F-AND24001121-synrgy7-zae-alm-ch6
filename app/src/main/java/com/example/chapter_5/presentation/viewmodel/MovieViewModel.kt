package com.example.chapter_5.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chapter_5.common.Resource
import com.example.chapter_5.data.remote.response.ResultResponse
import com.example.chapter_5.domain.model.Movie
import com.example.chapter_5.domain.model.MovieResult
import com.example.chapter_5.domain.usecase.MovieUseCase
import com.example.chapter_5.helper.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val pref: DataStoreManager) : ViewModel() {

    private val _movieListState: MutableStateFlow<Resource<Movie>> = MutableStateFlow(Resource.Loading())
    val movieListState: StateFlow<Resource<Movie>> = _movieListState

    private val _movieDetailState = MutableLiveData<Resource<MovieResult>>()
    val movieDetailState: LiveData<Resource<MovieResult>> get() = _movieDetailState

    private val _isDataLoaded: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDataLoaded: StateFlow<Boolean> = _isDataLoaded


    private val _movieDetailResponse = MutableLiveData<ResultResponse?>()
    val movieDetailResponse: LiveData<ResultResponse?> get() = _movieDetailResponse

    init {
        getNowPlayingMovies()
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            try {
                Log.d("MovieViewModel", "Fetching now playing movies...")
                movieUseCase().collect { resource ->
                    _movieListState.value = resource
                    if (resource is Resource.Success) {
                        _isDataLoaded.value = true
                        Log.d("MovieViewModel", "Data successfully loaded: ${resource.data?.results}")
                    } else if (resource is Resource.Error) {
                        _isDataLoaded.value = false
                        Log.e("MovieViewModel", "Error loading data: ${resource.message}")
                    }
                }
            } catch (e: Exception) {
                _movieListState.value = Resource.Error<Movie>(e.localizedMessage ?: "An unexpected error occurred")
                _isDataLoaded.value = false
                Log.e("MovieViewModel", "Exception occurred: ${e.localizedMessage}")
            }
        }
    }

    fun getMovieDetails(movieId: Int){
        viewModelScope.launch {
            movieUseCase.getDetailMovie(movieId).collect(){result->
                _movieDetailState.value = result
            }
        }

    }
}
