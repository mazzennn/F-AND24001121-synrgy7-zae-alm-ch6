package com.example.chapter_5.data.repository

import com.example.chapter_5.data.remote.MovieApi
import com.example.chapter_5.data.remote.response.MovieResponse
import com.example.chapter_5.data.remote.response.ResultResponse
import com.example.chapter_5.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi): MovieRepository {


    override suspend fun getNowPlayingMovies(): MovieResponse = movieApi.getNowPlayingMovies()

    override suspend fun getMovieDetails(movieId: Int): ResultResponse = movieApi.getMovieDetails(movieId)

}
