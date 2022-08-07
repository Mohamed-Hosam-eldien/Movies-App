package com.example.movies.ui.home.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class MovieItemBinding {

    companion object {
        @BindingAdapter("loadMovieImage")
        @JvmStatic
        fun loadMovieImage(imageView: ImageView, imageUrl: String?) {
            Glide.with(imageView).load("https://image.tmdb.org/t/p/w500$imageUrl").into(imageView)
        }
    }

}