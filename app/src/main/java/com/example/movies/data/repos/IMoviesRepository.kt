package com.example.movies.data.repos

import com.example.movies.data.models.genre.GenreList
import com.example.movies.data.models.movie.MoviesResponse
import com.example.movies.utils.NetworkResult
import retrofit2.Response

interface IMoviesRepository {

    suspend fun getMoviesByPageNumber(
        pageNumber:String, genreId:String = ""
    ) : Response<MoviesResponse>

    suspend fun getGenres(): Response<GenreList>

    suspend fun getMoviesBySearch(query:String, pageNumber:String): Response<MoviesResponse>
}