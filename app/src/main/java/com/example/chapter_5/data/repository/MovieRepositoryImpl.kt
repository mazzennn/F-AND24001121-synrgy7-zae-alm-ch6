package com.example.chapter_5.data.repository

import android.util.Log
import com.example.chapter_5.common.Resource
import com.example.chapter_5.data.remote.MovieApi
import com.example.chapter_5.data.remote.response.MovieResponse
import com.example.chapter_5.data.remote.response.toMovieResult
import com.example.chapter_5.domain.model.MovieResult
import com.example.chapter_5.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi): MovieRepository {


    override suspend fun getNowPlayingMovies(): Flow<Resource<MovieResponse>> = flow {
        emit(Resource.Loading())
        try {
            Log.d("MovieRepository", "Calling API to fetch now playing movies...")
            val response = movieApi.getNowPlayingMovies()
            Log.d("MovieRepository", "API call successful: ${response.results}")

            // Log the entire response object to ensure it contains expected data
            Log.d("MovieRepository", "Full response: $response")

            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e("MovieRepository", "API call failed: ${e.localizedMessage}")
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieResult>> = flow {
        emit(Resource.Loading())
        try {
            val response = movieApi.getMovieDetails(movieId)
            emit(Resource.Success(response.toMovieResult()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}
