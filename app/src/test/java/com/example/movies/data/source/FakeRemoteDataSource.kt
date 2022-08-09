package com.example.movies.data.source

import com.example.movies.data.models.genre.GenreResponse
import com.example.movies.data.models.movie.MoviesResponse
import com.example.movies.data.source.remote.IRemoteDataSource
import retrofit2.Response

class FakeRemoteDataSource(
    private val movieResponse: MoviesResponse,
    private val genreResponse: GenreResponse
) : IRemoteDataSource {

    override suspend fun getMoviesByPageNumber(
        pageNumber: String,
        genre: String
    ): Response<MoviesResponse> {
        return Response.success(movieResponse)
    }

    override suspend fun getGenres(): Response<GenreResponse> {
        return Response.success(genreResponse)
    }

    override suspend fun getMoviesBySearch(
        query: String,
        pageNumber: String
    ): Response<MoviesResponse> {
        return Response.success(movieResponse)
    }
}