package com.example.movies.data.repos

import com.example.movies.data.models.genre.GenreResponse
import com.example.movies.data.models.movie.MoviesResponse
import retrofit2.Response

interface IMoviesRepository {

    suspend fun getMoviesByPageNumber(
        pageNumber:String, genreId:String = ""
    ) : Response<MoviesResponse>

    suspend fun getGenres(): Response<GenreResponse>

    suspend fun getMoviesBySearch(query:String, pageNumber:String): Response<MoviesResponse>
}