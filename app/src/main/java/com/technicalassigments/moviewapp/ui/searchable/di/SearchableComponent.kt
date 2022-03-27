package com.technicalassigments.moviewapp.ui.searchable.di

import com.technicalassigments.movieapp.cache.di.component.CacheComponent
import com.technicalassigments.movieapp.domain.di.component.DomainComponent
import com.technicalassigments.movieapp.network.di.component.NetworkComponent
import com.technicalassigments.moviewapp.di.scope.FeatureScope
import com.technicalassigments.moviewapp.ui.searchable.view.SearchableActivity
import dagger.Component

@FeatureScope
@Component(
    modules = [
        SearchableModule::class
    ],
    dependencies = [
        NetworkComponent::class,
        CacheComponent::class,
        DomainComponent::class
    ]
)
interface SearchableComponent {

    fun inject(searchableActivity: SearchableActivity)

}