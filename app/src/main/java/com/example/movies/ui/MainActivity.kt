package com.example.movies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.movies.R
import com.example.movies.ui.home.HomeViewModel
import com.example.movies.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val moviesViwModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesViwModel.getMoviesByPageNumber(Constants.language, "1")
        moviesViwModel.getAllMovies.observe(this) {
            Log.e("TAG", "onCreate MOVIES: $it")
        }

    }
}