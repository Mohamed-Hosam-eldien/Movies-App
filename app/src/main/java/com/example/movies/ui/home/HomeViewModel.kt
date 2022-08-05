package com.example.movies.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.models.genre.GenreList
import com.example.movies.data.models.movie.Movie
import com.example.movies.data.repos.IMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : ViewModel() {

    private var allMovies: MutableLiveData<Movie> = MutableLiveData()
    var getAllMovies: LiveData<Movie> = allMovies

    private var genres: MutableLiveData<GenreList> = MutableLiveData()
    var getAllGenres: LiveData<GenreList> = genres


    fun getMoviesByPageNumber(language: String, pageNumber: String) = viewModelScope.launch {
        val moviesResult = moviesRepository.getMoviesByPageNumber(language, pageNumber)
        allMovies.postValue(moviesResult.body())
    }

    fun getGenre(language: String) = viewModelScope.launch {
        val genreResult = moviesRepository.getGenre(language)
        genres.postValue(genreResult.body())
    }

}