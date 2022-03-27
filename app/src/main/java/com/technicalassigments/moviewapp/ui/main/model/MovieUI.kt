package com.technicalassigments.moviewapp.ui.main.model

import android.os.Parcelable
import com.technicalassigments.movieapp.domain.mapper.Mapper
import com.technicalassigments.movieapp.network.dto.MovieResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUI(
    var id: Int,
    var original_title: String?,
    var overview: String?,
    var popularity: Double?,
    var poster_path: String?,
    var release_date: String?,
    var title: String?,
    var vote_average: Double?
) : Parcelable {

    internal class MovieResponseToMovie : Mapper<MovieResponse.Movie, MovieUI> {
        override fun mapFrom(response: MovieResponse.Movie): MovieUI {
            return MovieUI(
                id = response.id,
                original_title = response.original_title,
                overview = response.overview,
                popularity = response.popularity,
                poster_path = response.poster_path,
                release_date = response.release_date,
                title = response.title,
                vote_average = response.vote_average
            )

        }
    }

}