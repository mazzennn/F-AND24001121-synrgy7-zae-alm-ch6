package com.example.chapter_5.api

import com.example.chapter_5.ApiKey
import com.example.chapter_5.model.response.MovieResponse
import com.example.chapter_5.model.response.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    @GET("movie/now_playing")
    fun getMovieNowPlaying(
        @Header("Authorization") apikey: String = ApiKey.token
    ): Call<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Header("Authorization") apikey: String = ApiKey.token,
        @Path("movie_id") movieId: String
    ): Call<Result>

}