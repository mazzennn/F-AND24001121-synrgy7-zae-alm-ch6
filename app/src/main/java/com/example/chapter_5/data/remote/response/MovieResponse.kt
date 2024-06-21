package com.example.chapter_5.data.remote.response

import com.example.chapter_5.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("dates")
    val dates: Dates,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ResultResponse>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

fun MovieResponse.toMovie(): Movie {
    return Movie(
        dates = dates,
        page = page,
        results = results.map { it.toMovieResult() },
        totalPages = totalPages,
        totalResults = totalResults
    )
}
