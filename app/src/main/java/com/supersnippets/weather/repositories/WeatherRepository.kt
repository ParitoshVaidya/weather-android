package com.supersnippets.weather.repositories

import android.location.Location
import com.supersnippets.weather.models.ForecastDto
import com.supersnippets.weather.models.WeatherDto
import com.supersnippets.weather.service.ApiService

class WeatherRepository(private val apiService: ApiService) : BaseRepository() {
    fun getWeather(
        location: Location,
        onSuccess: (WeatherDto) -> Unit,
        onFailure: (Throwable?) -> Unit
    ) {
        val strLocation =
            "%.4f".format(location.latitude).toString() + "," + "%.4f".format(location.longitude)
        apiService.getWeather(strLocation).makeCall {
            onResponseSuccess = {
                println("-- on success")
                if (it.body()?.success == false) {
                    println("-- on failure")
                    onFailure(Throwable())
                } else {
                    val forecastList = listOf(
                        ForecastDto("Monday", "20"),
                        ForecastDto("Tuesday", "38"),
                        ForecastDto("Wednesday", "28"),
                        ForecastDto("Thursday", "32")
                    )

                    val weatherDto = WeatherDto(
                        it.body()?.current?.temperature,
                        it.body()?.location?.name,
                        forecastList
                    )
                    onSuccess(weatherDto)
                }
            }
            onResponseFailure = {
                println("-- on failure")
                it?.printStackTrace()
                onFailure(it)
            }
        }
    }
}