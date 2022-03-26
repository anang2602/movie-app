package com.technicalassigments.moviewapp.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.technicalassigments.movieapp.domain.usecase.FetchGenreUseCaseImpl
import kotlinx.coroutines.Dispatchers

class DaggerMainViewModel(
    private val fetchGenreUseCaseImpl: FetchGenreUseCaseImpl
): ViewModel() {

    val fetchGenre = liveData {
        val letGenreFlow = fetchGenreUseCaseImpl.retrieveGenre().asLiveData(Dispatchers.Main)
        emitSource(letGenreFlow)
    }

}