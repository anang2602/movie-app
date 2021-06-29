package com.technicalassigments.moviewapp.data.model

data class MovieResponse(
    var page: Int?,
    var results: List<Movie>?,
    var total_pages: Int?,
    var total_results: Int?
)