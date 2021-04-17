package com.supersnippets.weather.service

import com.supersnippets.weather.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("forecast?access_key=d643aa8c691e736acb6cd43c8a28e69d&query=Pune")
    fun getWeather(): Call<WeatherResponse>

}