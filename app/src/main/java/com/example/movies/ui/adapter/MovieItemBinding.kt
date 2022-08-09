package com.example.movies.ui.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.movies.utils.Constants

class MovieItemBinding {

    companion object {
        @BindingAdapter("loadMovieImage")
        @JvmStatic
        fun loadMovieImage(imageView: ImageView, imageUrl: String?) {
            Glide.with(imageView)
                .load("${Constants.IMAGE_URL}$imageUrl").into(imageView)
        }
    }
}