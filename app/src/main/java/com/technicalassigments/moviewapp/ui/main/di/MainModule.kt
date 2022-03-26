package com.technicalassigments.moviewapp.ui.main.di

import com.technicalassigments.movieapp.cache.database.dao.GenreDao
import com.technicalassigments.movieapp.domain.repository.GenreRepository
import com.technicalassigments.movieapp.domain.usecase.FetchGenreUseCaseImpl
import com.technicalassigments.movieapp.domain.utils.NetworkUtils
import com.technicalassigments.movieapp.network.services.GenreServices
import com.technicalassigments.moviewapp.di.scope.FeatureScope
import com.technicalassigments.moviewapp.ui.main.view.MainActivity
import com.technicalassigments.moviewapp.ui.main.viewmodel.DaggerMainViewModel
import com.technicalassigments.moviewapp.ui.main.viewmodel.viewModel
import dagger.Module
import dagger.Provides

@Module
class MainModule(
    val activity: MainActivity
) {

    @Provides
    @FeatureScope
    fun provideGenreRepository(
        networkUtils: NetworkUtils,
        genreServices: GenreServices,
        genreDao: GenreDao
    ) = GenreRepository(networkUtils, genreServices, genreDao)

    @Provides
    @FeatureScope
    fun provideGenreUseCaseImpl(
        repository: GenreRepository
    ) = FetchGenreUseCaseImpl(repository)

    @Provides
    @FeatureScope
    fun provideViewModel(
        fetchGenreUseCaseImpl: FetchGenreUseCaseImpl
    ) = activity.viewModel {
        DaggerMainViewModel(fetchGenreUseCaseImpl)
    }

}