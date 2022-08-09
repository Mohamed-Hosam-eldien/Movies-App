package com.example.movies.data.models.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoviesResponse(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val results: ArrayList<Result> = arrayListOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
): Serializable