package com.technicalassigments.moviewapp.ui.main.model

import com.technicalassigments.movieapp.cache.entity.GenreEntity
import com.technicalassigments.movieapp.domain.mapper.Mapper

data class GenreMovieUI(
    val id: Int,
    val name: String
) {

    internal class GenreEntityToGenreMovie : Mapper<Collection<GenreEntity>, Collection<GenreMovieUI>> {
        override fun mapFrom(response: Collection<GenreEntity>): Collection<GenreMovieUI> {
            return response.map {
                GenreMovieUI(
                    id = it.id,
                    name = it.name
                )
            }
        }
    }

}