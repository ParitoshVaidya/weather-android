package com.supersnippets.weather.models


data class WeatherResponse(
    val success: Boolean?,
    val location: Location,
    val current: Current
)

data class Location(
    val name: String
)

data class Current(
    val temperature: Int
)