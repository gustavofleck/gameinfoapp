package com.gustavo.architectureapp.utils

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinTestApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KoinTestApp)
            modules(emptyList())
        }
    }

}