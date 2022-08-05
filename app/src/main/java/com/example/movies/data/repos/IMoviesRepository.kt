package com.example.movies.data.repos

import com.example.movies.data.models.movie.Movie
import retrofit2.Response

interface IMoviesRepository {

    suspend fun getMoviesByPageNumber(
        language:String, pageNumber:String
    ) : Response<Movie>

}