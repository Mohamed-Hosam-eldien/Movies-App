package com.example.movies.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.movies.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.snackbar.Snackbar

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

fun Fragment.showSnackBar(message: String) {
    val snack:Snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_INDEFINITE)
        snack.setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).setBackgroundTint(
            this.requireActivity().getColor(R.color.purple_500)
        )
        .setActionTextColor(this.requireActivity().getColor(R.color.orange))
        .setAction(getString(R.string.close)) {
            snack.dismiss()
        }.show()
}