package com.example.movies.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.data.models.movie.Result
import com.example.movies.databinding.MovieItemBinding

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.GenresViewHolder>() {

    private var moviesList = emptyList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        return GenresViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val currentGenre = moviesList[position]
        holder.bind(currentGenre)
    }

    override fun getItemCount(): Int = moviesList.size

    class GenresViewHolder(private val binding: MovieItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(movie: Result) {
                binding.result = movie
                binding.executePendingBindings()
            }
    }

    fun setMoviesList(newData: List<Result>) {
        val moviesDiffUtil = MoviesDiffUtil(moviesList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(moviesDiffUtil)
        moviesList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

}