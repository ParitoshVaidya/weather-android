package com.supersnippets.weather.viewmodels

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.supersnippets.weather.models.WeatherDto
import com.supersnippets.weather.repositories.LocationRepository
import com.supersnippets.weather.repositories.WeatherRepository

class WeatherViewModel(
    private val weatherRepo: WeatherRepository,
    private val locationRepo: LocationRepository
) : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Boolean>()
    val weatherLiveData = MutableLiveData<WeatherDto>()

    fun fetchWeather() {
        isLoading.value = true
        isError.value = false
        locationRepo.getLocation(::getLocationSuccess, ::onFailure)
    }

    private fun getLocationSuccess(location: Location) {
        weatherRepo.getWeather(location, ::getWeatherSuccess, ::onFailure)
    }

    private fun getWeatherSuccess(dto: WeatherDto) {
        weatherLiveData.value = dto
        isLoading.value = false
    }

    private fun onFailure(throwable: Throwable?) {
        isLoading.value = false
        isError.value = true
    }
}