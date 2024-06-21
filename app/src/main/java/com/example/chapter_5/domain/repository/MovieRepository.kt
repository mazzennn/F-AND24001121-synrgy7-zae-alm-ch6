package com.example.chapter_5.domain.repository

import com.example.chapter_5.common.Resource
import com.example.chapter_5.data.remote.response.MovieResponse
import com.example.chapter_5.domain.model.MovieResult
import kotlinx.coroutines.flow.Flow

interface  MovieRepository {
    suspend fun getNowPlayingMovies(): Flow<Resource<MovieResponse>>
    suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieResult>>
}