package com.technicalassigments.moviewapp.ui.detailmovie.di

import com.technicalassigments.movieapp.domain.repository.reviews.ReviewsRepository
import com.technicalassigments.movieapp.domain.repository.videos.VideosRepository
import com.technicalassigments.movieapp.domain.usecase.FetchReviewsUseCaseImpl
import com.technicalassigments.movieapp.domain.usecase.FetchVideoUseCaseImpl
import com.technicalassigments.movieapp.domain.utils.NetworkUtils
import com.technicalassigments.movieapp.network.services.ReviewsService
import com.technicalassigments.movieapp.network.services.VideoService
import com.technicalassigments.moviewapp.di.scope.FeatureScope
import com.technicalassigments.moviewapp.ui.detailmovie.view.DetailMovieActivity
import com.technicalassigments.moviewapp.ui.detailmovie.viewmodel.DetailMovieViewModel
import com.technicalassigments.moviewapp.utils.viewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class DetailMovieModule(
    private val activity: DetailMovieActivity
) {

    @Provides
    @FeatureScope
    fun provideVideoRepository(
        networkUtils: NetworkUtils,
        videoService: VideoService
    ) = VideosRepository(networkUtils, videoService, Dispatchers.IO)

    @Provides
    @FeatureScope
    fun provideReviewRepository(
        reviewsService: ReviewsService
    ) = ReviewsRepository(reviewsService)

    @Provides
    @FeatureScope
    fun provideVideoUseCaseImpl(
        repository: VideosRepository
    ) = FetchVideoUseCaseImpl(repository)

    @Provides
    @FeatureScope
    fun provideReviewUseCaseImpl(
        repository: ReviewsRepository
    ) = FetchReviewsUseCaseImpl(repository)

    @Provides
    @FeatureScope
    fun provideViewModel(
        fetchVideoUseCaseImpl: FetchVideoUseCaseImpl,
        fetchReviewsUseCaseImpl: FetchReviewsUseCaseImpl
    ) = activity.viewModel {
        DetailMovieViewModel(fetchVideoUseCaseImpl, fetchReviewsUseCaseImpl)
    }

}