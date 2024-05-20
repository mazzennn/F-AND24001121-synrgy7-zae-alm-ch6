package com.example.chapter_5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chapter_5.model.response.MovieResponse
import com.example.chapter_5.repository.MovieRepository

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private var _movieResponse = MutableLiveData<MovieResponse?>()
    val movieResponse: LiveData<MovieResponse?> get() = _movieResponse

    //async
//    fun searchMovie(query: String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(null))
//        try {
//            emit(Resource.success(data = repository.searchMovie(query)))
//        } catch (exception: Exception) {
//            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
//        }
//    }

//    fun getMoviePopular() = liveData(Dispatchers.IO) {
//        emit(repository.moviePopular())
//    }
//
//    fun getMovieNowPlaying() {
//        ApiClient.instance.getMovieNowPlaying().enqueue(object : Callback<MovieResponse> {
//            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
//                val data = response.body()
//                _movieResponse.postValue(data)
//            }
//
//            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
//                _movieResponse.postValue(null)
//            }
//
//        })
//    }
}
