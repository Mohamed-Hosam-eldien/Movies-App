package com.example.movies.utils

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.movies.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}


fun Chip.initGenreChip(text: String, context: Context) {
    if (this.text == text) {
        this.isCheckable = true
        this.isChecked = true
    }

    /** too add custom style to chip */
    this.setChipDrawable(
        ChipDrawable.createFromAttributes(
            context,
            null,
            0,
            R.style.CustomChipStyle
        )
    )
}