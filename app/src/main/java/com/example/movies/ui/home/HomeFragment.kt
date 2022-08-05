package com.example.movies.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.movies.R
import com.example.movies.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val moviesViwModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        moviesViwModel.getMoviesByPageNumber(Constants.language, "1")
        moviesViwModel.getAllMovies.observe(requireActivity()) {
            Log.e("TAG", "onCreate MOVIES: $it")
        }

        moviesViwModel.getGenre(Constants.language)
        moviesViwModel.getAllGenres.observe(requireActivity()) {
            Log.e("TAG", "onCreate GENRES: $it")
        }

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        return view
    }

}