package com.example.chapter_5.domain.model

import com.example.chapter_5.data.remote.response.Dates

data class Movie(
    val dates: Dates,
    val page: Int,
    val results: List<MovieResult>,
    val totalPages: Int,
    val totalResults: Int
)
