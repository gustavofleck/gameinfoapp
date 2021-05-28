package com.gustavo.architectureapp.application

import android.app.Application
import com.gustavo.architectureapp.di.gamesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GamesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GamesApplication)
            modules(gamesModule)
        }
    }
}