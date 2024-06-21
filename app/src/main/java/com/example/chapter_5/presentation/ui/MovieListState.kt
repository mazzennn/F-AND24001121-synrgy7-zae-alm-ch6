package com.example.chapter_5.presentation.ui

import com.example.chapter_5.domain.model.Movie

sealed interface MovieListState{
    object Loading : MovieListState
    class Success(val movies: List<Movie>) : MovieListState
    class Error(val error: String) : MovieListState
}