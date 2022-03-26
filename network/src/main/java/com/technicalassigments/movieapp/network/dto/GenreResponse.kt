package com.technicalassigments.movieapp.network.dto

data class GenreResponse(
    var genres: List<Genre>?
) {
    data class Genre(
        var id: Int,
        var name: String
    )
}
