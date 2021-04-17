package com.supersnippets.weather.repositories

import androidx.lifecycle.MutableLiveData
import com.supersnippets.weather.helpers.NoInternetException
import com.supersnippets.weather.service.ApiService

class WeatherRepository(private val apiService: ApiService) : BaseRepository() {
    var weatherLiveData = MutableLiveData<String>()
    private val TAG = "WeatherRepository"

    companion object {
        @Volatile
        private var instance: WeatherRepository? = null

        fun getInstance(apiService: ApiService): WeatherRepository =
            instance ?: synchronized(this) {
                instance ?: WeatherRepository(apiService).also { instance = it }
            }
    }

    fun getWeather(): MutableLiveData<String> {
        apiService.getWeather().makeCall {
            onResponseSuccess = {
                println("-- on success")

            }
            onResponseFailure = {
                //callBack.onError(it)
                println("-- on failure")
                if (it is NoInternetException) {
                    print("-- no internet")


                } else {
                    println("-- on failure else part")
                }
                it?.printStackTrace()
            }

        }
        return MutableLiveData()
    }
}