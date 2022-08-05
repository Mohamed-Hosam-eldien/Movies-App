package com.example.movies.data.source.remote

import com.example.movies.data.models.movie.Movie
import retrofit2.Response

interface IRemoteDataSource {

    suspend fun getMoviesByPageNumber(
        language:String, pageNumber:String
    ) : Response<Movie>

}