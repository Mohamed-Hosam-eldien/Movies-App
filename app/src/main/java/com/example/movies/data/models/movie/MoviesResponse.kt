package com.example.movies.data.models.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoviesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: ArrayList<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
): Serializable