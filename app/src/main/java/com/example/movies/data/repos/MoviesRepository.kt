package com.example.movies.data.repos

import com.example.movies.data.models.genre.GenreList
import com.example.movies.data.models.movie.Movie
import com.example.movies.data.source.remote.IRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: IRemoteDataSource
) : IMoviesRepository {

    override suspend fun getMoviesByPageNumber(
        language: String, pageNumber: String
    ): Response<Movie> {
        return remoteDataSource.getMoviesByPageNumber(language, pageNumber)
    }

    override suspend fun getGenre(language: String): Response<GenreList> {
        return remoteDataSource.getGenres(language)
    }

}