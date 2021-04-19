package com.supersnippets.weather.di

import com.supersnippets.weather.repositories.LocationRepository
import com.supersnippets.weather.repositories.WeatherRepository
import com.supersnippets.weather.viewmodels.WeatherViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val weatherModule = module {
    single { WeatherRepository(get()) }
    single { LocationRepository(androidContext()) }
    factory { WeatherViewModel(get(), get()) }
}