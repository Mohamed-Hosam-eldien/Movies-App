package com.example.movies.data.models.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
): Serializable