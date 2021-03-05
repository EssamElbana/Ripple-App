package com.essam.rippleapp

import android.app.Application
import com.essam.rippleapp.di.applicationModule
import org.koin.android.ext.android.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(applicationModule))
    }
}