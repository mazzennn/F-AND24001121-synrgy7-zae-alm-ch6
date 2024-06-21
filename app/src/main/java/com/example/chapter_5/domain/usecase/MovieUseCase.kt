package com.example.chapter_5.domain.usecase

import android.util.Log
import com.example.chapter_5.common.Resource
import com.example.chapter_5.data.remote.response.toMovie
import com.example.chapter_5.domain.model.Movie
import com.example.chapter_5.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<Movie>> = flow {
        try {
            emit(Resource.Loading())
            val startTime = System.currentTimeMillis()

            // Panggil repository untuk mendapatkan response dari now playing movies
            Log.d("MovieUseCase", "Fetching now playing movies...")
            val movieResponseResource = repository.getNowPlayingMovies().first()
            val duration = System.currentTimeMillis() - startTime
            Log.d("MovieUseCase", "API call duration: $duration ms")

            // Handle Resource.Success case
            if (movieResponseResource is Resource.Success) {
                val movieResponse = movieResponseResource.data
                movieResponse?.let {
                    Log.d("MovieUseCase", "Data received: ${it.results}")
                    emit(Resource.Success(it.toMovie()))
                } ?: run {
                    Log.e("MovieUseCase", "MovieResponse is null")
                    emit(Resource.Error<Movie>("MovieResponse is null"))
                }
            } else if (movieResponseResource is Resource.Error) {
                // Handle Resource.Error case
                Log.e("MovieUseCase", "Error received: ${movieResponseResource.message}")
                emit(Resource.Error<Movie>(movieResponseResource.message ?: "Unknown error"))
            }
        } catch (e: Exception) {
            // Handle exceptions
            Log.e("MovieUseCase", "Exception occurred: ${e.localizedMessage}")
            emit(Resource.Error<Movie>(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}

