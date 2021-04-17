package com.supersnippets.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.supersnippets.weather.repositories.WeatherRepository
import com.supersnippets.weather.service.ApiService
import org.koin.java.KoinJavaComponent

class WeatherViewModel : ViewModel() {
    private val apiService: ApiService by KoinJavaComponent.inject(ApiService::class.java)
    private val repo = WeatherRepository.getInstance(apiService)
    private val isLoading = MutableLiveData<Boolean>()

    var weatherLiveData = MutableLiveData<String>()

    fun getWeather(): MutableLiveData<String> {
        return weatherLiveData
    }

    fun fetchWeather(): MutableLiveData<String> {
        isLoading.value = true
        weatherLiveData = repo.getWeather()
        isLoading.value = false
        return weatherLiveData
    }

    fun getIsLoading(): LiveData<Boolean> {
        return isLoading
    }
}