package com.technicalassigments.movieapp.network.dto

data class ReviewsResponse(
    var id: Int?,
    var page: Int?,
    var results: List<Reviews>?,
    var total_pages: Int?,
    var total_results: Int?
) {
    data class Reviews(
        var author: String?,
        var author_details: AuthorDetails?,
        var content: String?,
        var created_at: String?,
        var id: String?,
        var updated_at: String?,
        var url: String?
    )

    data class AuthorDetails(
        var avatar_path: String?,
        var name: String?,
        var rating: Any?,
        var username: String?
    )
}
