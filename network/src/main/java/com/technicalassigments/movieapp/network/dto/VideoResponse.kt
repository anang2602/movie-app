package com.technicalassigments.movieapp.network.dto

data class VideoResponse(
    var id: Int?,
    var results: List<Video>?
) {
    data class Video(
        var id: String?,
        var iso_3166_1: String?,
        var iso_639_1: String?,
        var key: String?,
        var name: String?,
        var site: String?,
        var size: Int?,
        var type: String?
    )
}
