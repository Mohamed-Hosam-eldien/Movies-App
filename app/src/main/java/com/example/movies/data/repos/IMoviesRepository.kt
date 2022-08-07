package com.example.movies.data.repos

import com.example.movies.data.models.genre.GenreList
import com.example.movies.data.models.movie.Movie
import retrofit2.Response

interface IMoviesRepository {

    suspend fun getMoviesByPageNumber(
        pageNumber:String, genreId:String = ""
    ) : Response<Movie>

    suspend fun getGenres(): Response<GenreList>
}