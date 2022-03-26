package com.technicalassigments.moviewapp.ui.main.di

import com.technicalassigments.movieapp.cache.di.component.CacheComponent
import com.technicalassigments.movieapp.domain.di.component.DomainComponent
import com.technicalassigments.movieapp.network.di.component.NetworkComponent
import com.technicalassigments.moviewapp.di.scope.FeatureScope
import com.technicalassigments.moviewapp.ui.main.view.MainActivity
import dagger.Component

@FeatureScope
@Component(
    modules = [
        MainModule::class
    ],
    dependencies = [
        NetworkComponent::class,
        CacheComponent::class,
        DomainComponent::class
    ]
)
interface MainComponent {

    fun inject(mainActivity: MainActivity)

}