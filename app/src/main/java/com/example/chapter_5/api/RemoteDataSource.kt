package com.example.chapter_5.api

import com.example.chapter_5.data.remote.MovieApi
import com.example.chapter_5.data.remote.response.MovieResponse

class RemoteDataSource(private val apiService: MovieApi) {

    suspend fun getNowPlayingMovies(): MovieResponse {
        return apiService.getNowPlayingMovies()
    }
    suspend fun getMovieDetails(movieId: Int) = apiService.getMovieDetails(movieId = movieId)
}
