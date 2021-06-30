package com.technicalassigments.moviewapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    var id: Int?,
    var name: String?
): Parcelable