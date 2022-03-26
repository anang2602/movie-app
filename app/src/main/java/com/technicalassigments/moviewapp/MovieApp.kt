package com.technicalassigments.moviewapp

import android.app.Application
import android.content.Context
import com.technicalassigments.movieapp.cache.di.component.CacheComponent
import com.technicalassigments.movieapp.cache.di.component.DaggerCacheComponent
import com.technicalassigments.movieapp.cache.di.module.CacheModule
import com.technicalassigments.movieapp.domain.di.component.DaggerDomainComponent
import com.technicalassigments.movieapp.domain.di.component.DomainComponent
import com.technicalassigments.movieapp.domain.di.module.DomainModule
import com.technicalassigments.movieapp.network.di.component.DaggerNetworkComponent
import com.technicalassigments.movieapp.network.di.component.NetworkComponent
import com.technicalassigments.moviewapp.di.DaggerAppComponent

class MovieApp: Application() {

    lateinit var networkComponent: NetworkComponent
    lateinit var cacheComponent: CacheComponent
    lateinit var domainComponent: DomainComponent

    companion object {
        @JvmStatic
        fun networkComponent(context: Context) =
            (context.applicationContext as? MovieApp)?.networkComponent

        @JvmStatic
        fun cacheComponent(context: Context) =
            (context.applicationContext as? MovieApp)?.cacheComponent

        @JvmStatic
        fun domainComponent(context: Context) =
            (context.applicationContext as? MovieApp)?.domainComponent
    }

    override fun onCreate() {
        super.onCreate()
        initNetworkDependencyInjection()
        initCacheDependencyInjection()
        initDomainDependencyInjection()
        initAppDependencyInjection()
    }

    private fun initDomainDependencyInjection() {
        domainComponent = DaggerDomainComponent
            .builder()
            .domainModule(DomainModule(this))
            .build()
    }

    private fun initCacheDependencyInjection() {
        cacheComponent = DaggerCacheComponent
            .builder()
            .cacheModule(CacheModule(this))
            .build()
    }

    private fun initNetworkDependencyInjection() {
        networkComponent = DaggerNetworkComponent
            .builder()
            .build()
    }

    private fun initAppDependencyInjection() {
        DaggerAppComponent
            .builder()
            .networkComponent(networkComponent)
            .cacheComponent(cacheComponent)
            .domainComponent(domainComponent)
            .build()
            .inject(this)
    }
}