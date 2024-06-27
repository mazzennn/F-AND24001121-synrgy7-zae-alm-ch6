package com.example.chapter_5.domain.repository

import com.example.chapter_5.data.remote.response.MovieResponse
import com.example.chapter_5.data.remote.response.ResultResponse

interface  MovieRepository {
    suspend fun getNowPlayingMovies(): MovieResponse
    suspend fun getMovieDetails(movieId: Int): ResultResponse
}