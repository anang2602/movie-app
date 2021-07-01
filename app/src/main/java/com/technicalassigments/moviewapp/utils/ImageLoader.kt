package com.technicalassigments.moviewapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.technicalassigments.moviewapp.R


fun ImageView.load(context: Context, url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun ImageView.loadAvatar(context: Context, url: String) {
    Glide.with(context)
        .load(url)
        .error(R.drawable.ic_baseline_person_24)
        .into(this)
}

