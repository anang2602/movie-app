package com.technicalassigments.movieapp.domain.repository

import com.technicalassigments.movieapp.cache.database.dao.GenreDao
import com.technicalassigments.movieapp.cache.entity.GenreEntity
import com.technicalassigments.movieapp.domain.mapper.GenreToEntityMapper
import com.technicalassigments.movieapp.domain.usecase.FetchGenreUseCase
import com.technicalassigments.movieapp.domain.utils.ApiResponse
import com.technicalassigments.movieapp.domain.utils.NetworkBoundResource
import com.technicalassigments.movieapp.domain.utils.NetworkUtils
import com.technicalassigments.movieapp.domain.utils.Resource
import com.technicalassigments.movieapp.network.dto.GenreResponse
import com.technicalassigments.movieapp.network.services.GenreServices
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class GenreRepository(
    private val networkUtils: NetworkUtils,
    private val genreServices: GenreServices,
    private val genreDao: GenreDao
) : FetchGenreUseCase {

    override suspend fun fetchGenreMovie(): Flow<Resource<Collection<GenreEntity>>> {
        return object : NetworkBoundResource<Collection<GenreEntity>, GenreResponse>(networkUtils) {
            override suspend fun saveNetworkResult(item: GenreResponse) {
                genreDao.insertGenres(
                    GenreToEntityMapper().mapFrom(response = item)
                )
            }

            override fun shouldFetch(data: Collection<GenreEntity>?) = true

            override suspend fun fetchFromNetwork(): ApiResponse<GenreResponse> {
                return try {
                    val res = genreServices.getMoviesGenre()
                    ApiResponse.create(res)
                } catch (e: Exception) {
                    ApiResponse.create(e)
                }
            }

            override fun loadFromDb(): Flow<List<GenreEntity>> {
                return genreDao.fetchGenreMovie()
            }
        }.asFlow()
    }
}