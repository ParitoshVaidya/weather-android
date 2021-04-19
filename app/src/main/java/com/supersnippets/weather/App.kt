package com.supersnippets.weather

import android.app.Application
import com.supersnippets.weather.di.networkModule
import com.supersnippets.weather.di.weatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, weatherModule))
        }
    }
}