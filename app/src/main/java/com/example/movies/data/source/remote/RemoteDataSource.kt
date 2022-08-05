package com.example.movies.data.source.remote

import com.example.movies.data.models.movie.Movie
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val networkService: NetworkService
) : IRemoteDataSource {

    override suspend fun getMoviesByPageNumber(
        language: String, pageNumber: String
    ): Response<Movie> {
        return networkService.getMoviesByPageNumber(language, pageNumber)
    }

}