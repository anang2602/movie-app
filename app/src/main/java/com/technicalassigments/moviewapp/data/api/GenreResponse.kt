package com.technicalassigments.moviewapp.data.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreResponse(
    var genres: List<Genre>?
):Parcelable