package com.technicalassigments.moviewapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.load(context: Context, url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}

