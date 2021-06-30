package com.technicalassigments.moviewapp.data.api

import android.os.Parcelable
import com.technicalassigments.moviewapp.data.model.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiscoverMovieResponse(
    var page: Int?,
    var results: List<Movie>?,
    var total_pages: Int?,
    var total_results: Int?
): Parcelable