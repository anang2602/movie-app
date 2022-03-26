package com.technicalassigments.movieapp.domain.mapper

import com.technicalassigments.movieapp.cache.entity.GenreEntity
import com.technicalassigments.movieapp.network.dto.GenreResponse

class GenreToEntityMapper: Mapper<GenreResponse, Collection<GenreEntity>> {

    override fun mapFrom(response: GenreResponse): Collection<GenreEntity> {
        return response.genres?.map { res ->
            GenreEntity(
                id = res.id,
                name = res.name
            )
        } ?: emptyList()
    }
}