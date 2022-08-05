package com.example.movies.data.models.movie

data class Movie(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)