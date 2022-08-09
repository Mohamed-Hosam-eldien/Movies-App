package com.example.movies.data.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movies.data.models.genre.Genre
import com.example.movies.data.models.genre.GenreResponse
import com.example.movies.data.models.movie.MoviesResponse
import com.example.movies.data.repos.MoviesRepository
import com.example.movies.data.source.FakeRemoteDataSource
import com.example.movies.data.source.remote.IRemoteDataSource
import com.example.movies.getOrAwaitValue
import com.example.movies.ui.home.HomeViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.movies.data.models.movie.Result
import org.junit.Assert
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class FakeMovieViewModel {

    private lateinit var movieRepository: MoviesRepository
    private lateinit var fakeRemoteDataSource: IRemoteDataSource
    private lateinit var homeViewModel: HomeViewModel

    private val moviesList = arrayListOf(Result(title = "movie1"), Result(title = "movie2"), Result(title = "movie3"))
    private val movieResponse = MoviesResponse(results = moviesList)

    private val genreList = arrayListOf(Genre(name = "All", id = 1), Genre(name = "Action", id = 2))
    private val genreResponse = GenreResponse(genreList)


    @get:Rule
    val instanceTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        fakeRemoteDataSource = FakeRemoteDataSource(movieResponse, genreResponse)
        movieRepository = MoviesRepository(fakeRemoteDataSource)
        homeViewModel = HomeViewModel(movieRepository,app)
    }

    @Test
    fun getMovies_returnMoviesFromRemoteNotNull() {
        homeViewModel.getAllMovies(pageNumber = "1", genreId = "All")
        val value = homeViewModel.getAllMovies.getOrAwaitValue{  }
        Assert.assertNotNull(value)
    }


}