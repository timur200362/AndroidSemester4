package com.example.androidsemester4

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(
    @StringRes message: Int,
    duration: Int = Snackbar.LENGTH_SHORT
) = Snackbar
    .make(this, message, duration)
    .show()

fun View.showSnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_SHORT
) = Snackbar
    .make(this, message, duration)
    .show()

fun View.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(windowToken, 0)
}
