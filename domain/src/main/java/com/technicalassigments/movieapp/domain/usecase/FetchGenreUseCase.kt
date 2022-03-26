package com.technicalassigments.movieapp.domain.usecase

import com.technicalassigments.movieapp.cache.entity.GenreEntity
import com.technicalassigments.movieapp.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface FetchGenreUseCase {

    suspend fun fetchGenreMovie(): Flow<Resource<Collection<GenreEntity>>>

}