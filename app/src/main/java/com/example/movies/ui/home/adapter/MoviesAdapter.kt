package com.example.movies.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.data.models.movie.Result
import com.example.movies.databinding.MovieItemBinding

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private var moviesList = arrayListOf<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentGenre = moviesList[position]
        holder.bind(currentGenre)
    }

    override fun getItemCount(): Int = moviesList.size

    class MoviesViewHolder(private val binding: MovieItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Result) {
            binding.result = movie
            binding.executePendingBindings()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMoviesByPagination(newData: List<Result>) {
        moviesList.addAll(newData)
        notifyDataSetChanged()
    }

    fun setMoviesByGenre(newData: List<Result>) {
        val moviesDiffUtil = MoviesDiffUtil(moviesList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(moviesDiffUtil)
        moviesList.clear()
        moviesList.addAll(newData)
        diffUtilResult.dispatchUpdatesTo(this)
    }

}