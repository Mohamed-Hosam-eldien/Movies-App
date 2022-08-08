package com.example.movies.data.source.remote

import com.example.movies.data.models.genre.GenreList
import com.example.movies.data.models.movie.MoviesResponse
import com.example.movies.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("movie/popular?")
    suspend fun getMoviesByPageNumber(
        @Query("language") language: String = "en-US",
        @Query("page") page: String,
        @Query("with_genres") with_genres: String,
        @Query("api_key") api_key: String = Constants.API_KEY
    ): Response<MoviesResponse>

    @GET("genre/list?")
    suspend fun getGenres(
        @Query("api_key") api_key: String = Constants.API_KEY
    ): Response<GenreList>

    @GET("search/movie?")
    suspend fun getMoviesBySearch(
        @Query("query") query: String,
        @Query("page") page: String,
        @Query("api_key") api_key: String = Constants.API_KEY
    ): Response<MoviesResponse>

}