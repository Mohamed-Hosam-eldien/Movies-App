package com.example.movies.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.data.models.movie.Result
import com.example.movies.databinding.MovieItemBinding
import com.example.movies.utils.MoviesListState

class MoviesAdapter(private val onClickMovie: OnClickMovie) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

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
        val currentMovie = moviesList[position]
        holder.bind(currentMovie)
        holder.itemView.setOnClickListener { onClickMovie.onClick(currentMovie) }
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
    private fun setMoviesByPagination(newData: List<Result>) {
        moviesList.addAll(newData)
        notifyDataSetChanged()
    }

    fun setMoviesList(movieList: MoviesListState, newData: List<Result>) {
        when(movieList) {
            MoviesListState.PAGINATION -> setMoviesByPagination(newData)
            MoviesListState.GENRE -> setMoviesByGenre(newData)
        }
    }

    private fun setMoviesByGenre(newData: List<Result>) {
        val moviesDiffUtil = MoviesDiffUtil(moviesList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(moviesDiffUtil)
        moviesList.clear()
        moviesList.addAll(newData)
        diffUtilResult.dispatchUpdatesTo(this)
    }

}