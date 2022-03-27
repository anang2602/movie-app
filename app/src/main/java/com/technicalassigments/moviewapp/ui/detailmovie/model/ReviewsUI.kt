package com.technicalassigments.moviewapp.ui.detailmovie.model

import com.technicalassigments.movieapp.domain.mapper.Mapper
import com.technicalassigments.movieapp.network.dto.ReviewsResponse

data class ReviewsUI(
    var author: String?,
    var author_details: ReviewsResponse.AuthorDetails?,
    var content: String?,
    var created_at: String?,
    var id: String?,
    var updated_at: String?,
    var url: String?
) {

    data class AuthorDetails(
        var avatar_path: String?,
        var name: String?,
        var rating: Any?,
        var username: String?
    )

    internal class ReviewResponseToReviewUI : Mapper<ReviewsResponse.Reviews, ReviewsUI> {
        override fun mapFrom(response: ReviewsResponse.Reviews): ReviewsUI {
            return ReviewsUI(
                author = response.author,
                author_details = response.author_details,
                content = response.content,
                created_at = response.created_at,
                id = response.id,
                updated_at = response.updated_at,
                url = response.url
            )
        }
    }

}