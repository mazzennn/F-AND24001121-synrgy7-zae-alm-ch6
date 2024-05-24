package com.example.chapter_5.repository

import com.example.chapter_5.api.RemoteDataSource

class MovieRepository(private val remoteDataSource: RemoteDataSource) {
    suspend fun getMovieDetails(movieId : Int) = remoteDataSource.getMovieDetails(movieId)

//    suspend fun searchMovie(query: String) = remoteDataSource.searchMovie(query)
//    suspend fun moviePopular() = remoteDataSource.moviePopular()


}
