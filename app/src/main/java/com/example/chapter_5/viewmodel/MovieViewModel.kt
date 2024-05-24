package com.example.chapter_5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chapter_5.api.ApiClient
import com.example.chapter_5.helper.DataStoreManager
import com.example.chapter_5.model.response.MovieResponse
import com.example.chapter_5.model.response.Result
import com.example.chapter_5.repository.MovieRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel(private val repository: MovieRepository, private val pref: DataStoreManager) : ViewModel() {

    private var _movieResponse = MutableLiveData<MovieResponse?>()
    val movieResponse: LiveData<MovieResponse?> get() = _movieResponse

    private val _movieDetailResponse = MutableLiveData<Result?>()
    val movieDetailResponse: LiveData<Result?> get() = _movieDetailResponse



    fun getMovieNowPlaying() {
        ApiClient.instance.getMovieNowPlaying().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val data = response.body()
                _movieResponse.postValue(data)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _movieResponse.postValue(null)
            }

        })
    }

    fun getMovieDetails(movieId: Int){
        ApiClient.instance.getMovieDetails(movieId = movieId).enqueue(object : Callback<Result>{
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                val data = response.body()
                _movieDetailResponse.postValue(data)
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                _movieDetailResponse.postValue(null)
            }

        })

    }
}
