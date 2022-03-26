package com.technicalassigments.movieapp.domain.di.component

import com.technicalassigments.movieapp.domain.di.module.DomainModule
import com.technicalassigments.movieapp.domain.utils.NetworkUtils
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DomainModule::class
    ]
)
interface DomainComponent {

    fun networkUtils(): NetworkUtils

}