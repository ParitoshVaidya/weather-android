package com.supersnippets.weather.service

import com.supersnippets.weather.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("forecast")
    fun getWeather(@Query("query") location: String): Call<WeatherResponse>

}