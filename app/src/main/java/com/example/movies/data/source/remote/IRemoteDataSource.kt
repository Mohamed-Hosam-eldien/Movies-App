package com.example.movies.data.source.remote

import com.example.movies.data.models.genre.GenreList
import com.example.movies.data.models.movie.Movie
import retrofit2.Response

interface IRemoteDataSource {

    suspend fun getMoviesByPageNumber(
        pageNumber:String, genre:String
    ): Response<Movie>

    suspend fun getGenres(): Response<GenreList>
}