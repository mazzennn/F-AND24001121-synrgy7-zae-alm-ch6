package com.example.chapter_5.api

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getMovieDetails(movieId: Int) = apiService.getMovieDetails(movieId = movieId)
}
