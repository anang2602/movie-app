package com.technicalassigments.moviewapp.data.repository

import com.technicalassigments.moviewapp.BuildConfig
import com.technicalassigments.moviewapp.data.api.ApiService
import com.technicalassigments.moviewapp.data.model.Genre
import com.technicalassigments.moviewapp.data.model.GenreResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.HttpException
import java.io.IOException

private const val LANGUAGE = "en-US"

class GenreRepository(private val apiService: ApiService) {

    private val genreResults = MutableSharedFlow<GenreResult>(replay = 1)
    private var isRequestInProgress = false

    suspend fun getGenreResultStream(): Flow<GenreResult> {
        requestAndSaveData()
        return genreResults
    }

    private suspend fun requestAndSaveData(): Boolean {
        isRequestInProgress = true
        var sucessful = false

        try {
            val response = apiService.getMoviesGenre(BuildConfig.MY_API_KEY, LANGUAGE)
            val genres = response.genres ?: emptyList()
            genreResults.emit(GenreResult.Success(genres))
            sucessful = true
        } catch (e: IOException) {
            genreResults.emit(GenreResult.Error(e))
        } catch (e: HttpException) {
            genreResults.emit(GenreResult.Error(e))
        }
        isRequestInProgress = false
        return sucessful
    }

}