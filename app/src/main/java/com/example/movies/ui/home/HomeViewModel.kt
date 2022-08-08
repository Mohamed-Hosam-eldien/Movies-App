package com.example.movies.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.models.genre.GenreList
import com.example.movies.data.models.movie.MoviesResponse
import com.example.movies.data.repos.IMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : ViewModel() {

    private lateinit var allMovies: MutableLiveData<MoviesResponse>
    lateinit var getAllMovies: LiveData<MoviesResponse>

    private var genres: MutableLiveData<GenreList> = MutableLiveData()
    var getAllGenres: LiveData<GenreList> = genres


    fun getAllMovies(pageNumber: String, genreId: String) = viewModelScope.launch {
        allMovies = MutableLiveData()
        getAllMovies = allMovies
        val moviesResult = moviesRepository.getMoviesByPageNumber(pageNumber, genreId)
        allMovies.postValue(moviesResult.body())
    }

    fun getGenres() = viewModelScope.launch {
        val genresResult = moviesRepository.getGenres()
        genres.postValue(genresResult.body())
    }

}