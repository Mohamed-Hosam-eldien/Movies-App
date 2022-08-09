package com.example.movies.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.example.movies.R
import com.example.movies.data.models.genre.GenreList
import com.example.movies.data.models.movie.MoviesResponse
import com.example.movies.data.repos.IMoviesRepository
import com.example.movies.utils.Constants
import com.example.movies.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: IMoviesRepository,
    application: Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>()
    
    private lateinit var allMovies: MutableLiveData<NetworkResult<MoviesResponse>>
    lateinit var getAllMovies: LiveData<NetworkResult<MoviesResponse>>

    private var allGenres: MutableLiveData<NetworkResult<GenreList>> = MutableLiveData()
    var getAllGenres: LiveData<NetworkResult<GenreList>> = allGenres

    fun getAllMovies(pageNumber: String, genreId: String) = viewModelScope.launch {
        allMovies = MutableLiveData()
        getAllMovies = allMovies
        allMovies.postValue(NetworkResult.Loading())

        if (Constants.isNetworkAvailable(context)) {
            val moviesResult = moviesRepository.getMoviesByPageNumber(pageNumber, genreId)
            allMovies.postValue(handleMoviesResponseState(moviesResult))
        } else {
            allMovies.postValue(NetworkResult.Error(context
                .getString(R.string.no_network_connection)))
        }

    }

    private fun handleMoviesResponseState(
        response: Response<MoviesResponse>
    ): NetworkResult<MoviesResponse> {

        when {
            response.message().toString().contains(context.getString(R.string.timeout1)) -> {
                return NetworkResult.Error(context.getString(R.string.timeout2))
            }
            response.code() == 401 -> {
                return NetworkResult.Error(context.getString(R.string.api_invalid))
            }
            response.code() == 404 -> {
                return NetworkResult.Error(context.getString(R.string.not_found))
            }
            response.body()?.results.isNullOrEmpty() -> {
                return NetworkResult.Error(context.getString(R.string.no_movies))
            }
            response.isSuccessful -> {
                return NetworkResult.Success(response.body()!!)
            }
            else -> return NetworkResult.Error(response.message())
        }

    }

    fun getGenres() = viewModelScope.launch {
        if (Constants.isNetworkAvailable(context)) {
            val genresResult = moviesRepository.getGenres()
            allGenres.postValue(handleGenreResponseState(genresResult))
        } else {
            allGenres.postValue(NetworkResult.Error(context
                .getString(R.string.no_network_connection)))
        }
    }

    private fun handleGenreResponseState(
        response: Response<GenreList>
    ): NetworkResult<GenreList> {

        when {
            response.message().toString().contains(context.getString(R.string.timeout1)) -> {
                return NetworkResult.Error(context.getString(R.string.timeout2))
            }
            response.code() == 401 -> {
                return NetworkResult.Error(context.getString(R.string.api_invalid))
            }
            response.code() == 404 -> {
                return NetworkResult.Error(context.getString(R.string.not_found))
            }
            response.body()?.genres.isNullOrEmpty() -> {
                return NetworkResult.Error(context.getString(R.string.no_movies))
            }
            response.isSuccessful -> {
                return NetworkResult.Success(response.body()!!)
            }
            else -> return NetworkResult.Error(response.message())
        }
    }

}