package com.example.chapter_5.data.remote

import com.example.chapter_5.data.remote.response.MovieResponse
import com.example.chapter_5.data.remote.response.ResultResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): ResultResponse
}