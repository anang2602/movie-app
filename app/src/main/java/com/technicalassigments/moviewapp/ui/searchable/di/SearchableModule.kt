package com.technicalassigments.moviewapp.ui.searchable.di

import com.technicalassigments.movieapp.domain.repository.movie.MovieRepository
import com.technicalassigments.movieapp.domain.usecase.FetchMovieUseCaseImpl
import com.technicalassigments.movieapp.network.services.MovieServices
import com.technicalassigments.moviewapp.di.scope.FeatureScope
import com.technicalassigments.moviewapp.ui.searchable.view.SearchableActivity
import com.technicalassigments.moviewapp.ui.searchable.viewmodel.SearchViewModel
import com.technicalassigments.moviewapp.utils.viewModel
import dagger.Module
import dagger.Provides

@Module
class SearchableModule(
    private val activity: SearchableActivity
) {

    @Provides
    @FeatureScope
    fun provideMovieRepository(
        movieServices: MovieServices
    ) = MovieRepository(movieServices)

    @Provides
    @FeatureScope
    fun provideMovieUseCaseImpl(
        repository: MovieRepository
    ) = FetchMovieUseCaseImpl(repository)

    @Provides
    @FeatureScope
    fun provideViewModel(
        fetchMovieUseCaseImpl: FetchMovieUseCaseImpl
    ) = activity.viewModel {
        SearchViewModel(fetchMovieUseCaseImpl)
    }

}