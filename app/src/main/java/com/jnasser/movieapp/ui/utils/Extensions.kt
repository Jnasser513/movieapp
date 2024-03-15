package com.jnasser.movieapp.ui.utils

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.jnasser.movieapp.R

fun Context.messageToast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun View.showView() =
    this.visibility == View.VISIBLE

fun View.endShowView() =
    this.visibility == View.GONE

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run {
        navigate(direction)
    }
}

fun NavController.safeNavigateToAction(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run {
        navigate(direction)
    }
}

@BindingAdapter("timeInHoursMinutes")
fun TextView.setTimeInHoursMinutes(minutes: Int) {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    text = context.getString(R.string.fragment_movie_detail_lenght, hours, remainingMinutes)
}