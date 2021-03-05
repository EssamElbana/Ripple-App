package com.essam.rippleapp.di

import com.essam.rippleapp.data.RepositoryImp
import com.essam.rippleapp.data.server_gateway.ServerGateway
import com.essam.rippleapp.data.server_gateway.ServerGatewayFakeImp
import com.essam.rippleapp.domain.Repository
import com.essam.rippleapp.presentation.ReposListContract
import com.essam.rippleapp.presentation.ReposListPresenter

import org.koin.dsl.module.module

val applicationModule = module(override = true) {
    single<ServerGateway> {
        ServerGatewayFakeImp()
    }
    single<Repository> {
        RepositoryImp(get())
    }

    factory<ReposListContract.Presenter> { (view: ReposListContract.View) ->
        ReposListPresenter(
            view,
            get()
        )
    }

}