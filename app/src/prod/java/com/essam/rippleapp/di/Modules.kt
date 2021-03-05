package com.essam.rippleapp.di

import com.essam.rippleapp.data.RepositoryImp
import com.essam.rippleapp.data.ServerGatewayImp
import com.essam.rippleapp.data.cache.InMemoryCache
import com.essam.rippleapp.data.cache.InMemoryCacheImp
import com.essam.rippleapp.data.server_gateway.ServerGateway
import com.essam.rippleapp.domain.Repository
import com.essam.rippleapp.presentation.ReposListContract
import com.essam.rippleapp.presentation.ReposListPresenter


import org.koin.dsl.module.module

val applicationModule = module(override = true) {
    single<ServerGateway> {
        ServerGatewayImp()
    }

    single<InMemoryCache> {
        InMemoryCacheImp()
    }

    single<Repository> {
        RepositoryImp(get(), get())
    }

    factory<ReposListContract.Presenter> { (view: ReposListContract.View) ->
        ReposListPresenter(
            view,
            get()
        )
    }

}