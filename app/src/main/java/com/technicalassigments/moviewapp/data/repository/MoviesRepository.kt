package com.technicalassigments.moviewapp.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.technicalassigments.moviewapp.BuildConfig
import com.technicalassigments.moviewapp.data.api.ApiService
import com.technicalassigments.moviewapp.data.model.DiscoverMovieResult
import com.technicalassigments.moviewapp.data.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class MoviesRepository(private val apiService: ApiService) {

    fun getMoviesResultStream(genre: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { MoviesPagingSource(apiService, genre) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

}