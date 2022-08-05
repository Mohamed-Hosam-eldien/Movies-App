package com.example.movies.data.source.remote

import com.example.movies.data.models.movie.Movie
import com.example.movies.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("discover/movie?api_key=${Constants.API_KEY}")
    suspend fun getMoviesByPageNumber(
        @Query("language") language: String,
        @Query("page") page:String)
    : Response<Movie>

}