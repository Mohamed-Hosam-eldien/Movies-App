package com.example.movies.data.source.remote

import com.example.movies.data.models.genre.GenreList
import com.example.movies.data.models.movie.MoviesResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val networkService: NetworkService
) : IRemoteDataSource {

    override suspend fun getMoviesByPageNumber(
        pageNumber: String, genre: String
    ): Response<MoviesResponse> {
        return networkService.getMoviesByPageNumber(page = pageNumber, with_genres = genre)
    }

    override suspend fun getGenres(): Response<GenreList> {
        return networkService.getGenres()
    }

    override suspend fun getMoviesBySearch(query: String, pageNumber: String): Response<MoviesResponse> {
        return networkService.getMoviesBySearch(query = query, page = pageNumber)
    }

}