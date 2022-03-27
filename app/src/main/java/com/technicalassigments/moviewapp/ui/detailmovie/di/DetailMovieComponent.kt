package com.technicalassigments.moviewapp.ui.detailmovie.di

import com.technicalassigments.movieapp.cache.di.component.CacheComponent
import com.technicalassigments.movieapp.domain.di.component.DomainComponent
import com.technicalassigments.movieapp.network.di.component.NetworkComponent
import com.technicalassigments.moviewapp.di.scope.FeatureScope
import com.technicalassigments.moviewapp.ui.detailmovie.view.DetailMovieActivity
import dagger.Component

@FeatureScope
@Component(
    modules = [
        DetailMovieModule::class
    ],
    dependencies = [
        NetworkComponent::class,
        CacheComponent::class,
        DomainComponent::class
    ]
)
interface DetailMovieComponent {

    fun inject(detailMovieActivity: DetailMovieActivity)

}