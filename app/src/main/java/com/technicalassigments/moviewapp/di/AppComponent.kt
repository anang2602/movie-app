package com.technicalassigments.moviewapp.di

import com.technicalassigments.movieapp.cache.di.component.CacheComponent
import com.technicalassigments.movieapp.domain.di.component.DomainComponent
import com.technicalassigments.movieapp.network.di.component.NetworkComponent
import com.technicalassigments.moviewapp.MovieApp
import com.technicalassigments.moviewapp.di.scope.AppScope
import dagger.Component

@AppScope
@Component(
    dependencies = [
        NetworkComponent::class,
        CacheComponent::class,
        DomainComponent::class
    ]
)
interface AppComponent {

    fun inject(application: MovieApp)

}