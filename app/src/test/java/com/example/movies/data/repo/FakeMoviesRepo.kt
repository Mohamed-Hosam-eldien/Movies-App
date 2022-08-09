package com.example.movies.data.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movies.data.models.genre.GenreResponse
import com.example.movies.data.models.movie.MoviesResponse
import com.example.movies.data.repos.IMoviesRepository
import org.junit.Rule
import org.junit.runner.RunWith
import retrofit2.Response


@RunWith(AndroidJUnit4::class)
class FakeMoviesRepo : IMoviesRepository {

    @get:Rule
    val instanceTaskExecutorRule = InstantTaskExecutorRule()

    private val moviesList = MoviesResponse()
    private val genreResponse = GenreResponse()

    override suspend fun getMoviesByPageNumber(
        pageNumber: String,
        genreId: String
    ): Response<MoviesResponse> {
        return Response.success(moviesList)
    }

    override suspend fun getGenres(): Response<GenreResponse> {
        return Response.success(genreResponse)
    }

    override suspend fun getMoviesBySearch(
        query: String,
        pageNumber: String
    ): Response<MoviesResponse> {
        return Response.success(moviesList)
    }
}