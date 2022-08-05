package com.example.movies.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.R
import com.example.movies.databinding.FragmentHomeBinding
import com.example.movies.ui.home.adapter.MoviesAdapter
import com.example.movies.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val moviesViwModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val moviesAdapter by lazy { MoviesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // init binding
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)

        moviesViwModel.getGenre(Constants.language)
        moviesViwModel.getAllGenres.observe(requireActivity()) {
            Log.e("TAG", "onCreate GENRES: $it")
        }

        initRecyclerView()
        getMoviesData()

        return binding.root
    }

    fun initRecyclerView() = binding.recyclerMovies.apply {
        adapter = moviesAdapter
        layoutManager = GridLayoutManager(context, 2)
    }

    fun getMoviesData() {
        moviesViwModel.getMoviesByPageNumber(Constants.language, "1")
        moviesViwModel.getAllMovies.observe(requireActivity()) {
            Log.e("TAG", "onCreate MOVIES: $it")
            moviesAdapter.setMoviesList(it.results)
        }
    }


}