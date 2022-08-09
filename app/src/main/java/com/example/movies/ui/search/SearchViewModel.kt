package com.example.movies.ui.search

import android.app.Application
import androidx.lifecycle.*
import com.example.movies.R
import com.example.movies.data.models.movie.MoviesResponse
import com.example.movies.data.source.remote.RemoteDataSource
import com.example.movies.utils.Constants
import com.example.movies.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    application: Application
): AndroidViewModel(application) {

    private val context = getApplication<Application>()

    private lateinit var _getSearchResult: MutableLiveData<NetworkResult<MoviesResponse>>
    lateinit var getSearchResult: LiveData<NetworkResult<MoviesResponse>>

    fun getMoviesByQuery(query: String, pageNum: String) = viewModelScope.launch {
        _getSearchResult = MutableLiveData()
        getSearchResult = _getSearchResult

        if(Constants.isNetworkAvailable(context)) {
            val result = remoteDataSource.getMoviesBySearch(query, pageNum)
            _getSearchResult.postValue(handleSearchResponseState(result))
        } else {
            _getSearchResult.postValue(NetworkResult.Error(context.getString(R.string.no_network_connection)))
        }
    }

    private fun handleSearchResponseState(
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

}