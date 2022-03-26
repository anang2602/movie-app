package com.technicalassigments.moviewapp.ui.main.model

import com.technicalassigments.movieapp.cache.entity.GenreEntity
import com.technicalassigments.movieapp.domain.mapper.Mapper

data class GenreMovie(
    val id: Int,
    val name: String
) {

    internal class GenreEntityToGenreMovie : Mapper<Collection<GenreEntity>, Collection<GenreMovie>> {
        override fun mapFrom(response: Collection<GenreEntity>): Collection<GenreMovie> {
            return response.map {
                GenreMovie(
                    id = it.id,
                    name = it.name
                )
            }
        }
    }

}