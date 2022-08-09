package com.example.movies.data.source.remote

import com.example.movies.data.models.genre.GenreResponse
import com.example.movies.data.models.movie.MoviesResponse
import retrofit2.Response

interface IRemoteDataSource {

    suspend fun getMoviesByPageNumber(
        pageNumber:String, genre:String
    ): Response<MoviesResponse>

    suspend fun getGenres(): Response<GenreResponse>

    suspend fun getMoviesBySearch(query:String, pageNumber:String): Response<MoviesResponse>
}