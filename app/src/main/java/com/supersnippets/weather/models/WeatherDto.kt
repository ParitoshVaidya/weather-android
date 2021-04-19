package com.supersnippets.weather.models

data class WeatherDto(
    val temperature: Int?,
    val city: String?,
    var forecastList: List<ForecastDto>
)