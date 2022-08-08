package com.example.movies.data.repos

import com.example.movies.data.models.genre.GenreList
import com.example.movies.data.models.movie.MoviesResponse
import com.example.movies.data.source.remote.IRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: IRemoteDataSource
) : IMoviesRepository {

    override suspend fun getMoviesByPageNumber(
        pageNumber: String, genreId: String
    ): Response<MoviesResponse> {
        return remoteDataSource.getMoviesByPageNumber(pageNumber, genreId)
    }

    override suspend fun getGenres(): Response<GenreList> {
        return remoteDataSource.getGenres()
    }

    override suspend fun getMoviesBySearch(query: String, pageNumber: String): Response<MoviesResponse> {
        return remoteDataSource.getMoviesBySearch(query, pageNumber)
    }

}