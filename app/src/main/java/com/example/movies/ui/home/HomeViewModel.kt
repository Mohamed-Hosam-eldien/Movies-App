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


    fun getAllMovies(pageNumber: String, genreId: String) = viewModelScope.launch {
        allMovies = MutableLiveData()
        getAllMovies = allMovies
        val moviesResult = moviesRepository.getMoviesByPageNumber(pageNumber, genreId)
        allMovies.postValue(moviesResult.body())
    }

//    fun getMoviesByGenre(pageNumber: String, genreId: String) = viewModelScope.launch {
//        allMovies = MutableLiveData()
//        getAllMovies = allMovies
//        val moviesResult = moviesRepository.getMoviesByPageNumber(pageNumber, genreId = genreId)
//        allMovies.postValue(moviesResult.body())
//    }

    fun getGenre() = viewModelScope.launch {
        val genreResult = moviesRepository.getGenre()
        genres.postValue(genreResult.body())
    }

}