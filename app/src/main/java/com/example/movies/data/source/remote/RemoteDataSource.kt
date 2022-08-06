package com.example.movies.data.source.remote

import android.util.Log
import com.example.movies.data.models.genre.GenreList
import com.example.movies.data.models.movie.Movie
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val networkService: NetworkService
) : IRemoteDataSource {

    override suspend fun getMoviesByPageNumber(
        pageNumber: String, genre: String
    ): Response<Movie> {
        return networkService.getMoviesByPageNumber(page = pageNumber, with_genres = genre)
    }

    override suspend fun getGenres(): Response<GenreList> {
        return networkService.getGenres()
    }

}