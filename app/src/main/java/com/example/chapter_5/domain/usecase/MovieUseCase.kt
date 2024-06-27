package com.example.chapter_5.domain.usecase

import android.util.Log
import com.example.chapter_5.common.Resource
import com.example.chapter_5.data.remote.response.toMovie
import com.example.chapter_5.data.remote.response.toMovieResult
import com.example.chapter_5.domain.model.Movie
import com.example.chapter_5.domain.model.MovieResult
import com.example.chapter_5.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<Movie>> = flow {
        try {
            emit(Resource.Loading())
            val startTime = System.currentTimeMillis()
            val movie = repository.getNowPlayingMovies().toMovie()
            emit(Resource.Success(movie))
        } catch (e: Exception) {
            // Handle exceptions
            Log.e("MovieUseCase", "Exception occurred: ${e.localizedMessage}")
            emit(Resource.Error<Movie>(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    fun getDetailMovie(movieId: Int): Flow<Resource<MovieResult>> = flow {
        try {
            emit(Resource.Loading())
            val movieDetail = repository.getMovieDetails(movieId).toMovieResult()
            emit(Resource.Success(movieDetail))
        } catch (e: Exception) {
            // Handle exceptions
            Log.e("MovieUseCase", "Exception occurred: ${e.localizedMessage}")
            emit(Resource.Error<MovieResult>(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}

