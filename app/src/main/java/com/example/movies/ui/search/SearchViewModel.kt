package com.example.movies.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.models.movie.MoviesResponse
import com.example.movies.data.source.remote.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): ViewModel() {

    private var searchMovies: MutableLiveData<MoviesResponse> = MutableLiveData()
    var getSearchResult: LiveData<MoviesResponse> = searchMovies

    fun getMoviesByQuery(query: String, pageNum: String) = viewModelScope.launch {
        searchMovies = MutableLiveData()
        getSearchResult = searchMovies
        val result = remoteDataSource.getMoviesBySearch(query, pageNum)
        searchMovies.postValue(result.body())
    }

}