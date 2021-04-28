package com.supersnippets.weather.viewmodels

import android.location.Location
import android.location.LocationManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.supersnippets.weather.models.WeatherDto
import com.supersnippets.weather.repositories.LocationRepository
import com.supersnippets.weather.repositories.WeatherRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {
    private lateinit var viewModel: WeatherViewModel

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var weatherRepository: WeatherRepository

    @Mock
    lateinit var locationRepository: LocationRepository

    @Before
    fun setUp() {
        viewModel = WeatherViewModel(weatherRepository, locationRepository)
    }


    private val locationCaptor = argumentCaptor<(Location) -> Unit>()
    private val weatherCaptorMock = argumentCaptor<(WeatherDto) -> Unit>()
    private val throwableCaptorMock = argumentCaptor<(Throwable?) -> Unit>()
    private val throwableCaptor = argumentCaptor<(Throwable?) -> Unit>()

    @Test
    fun fetchWeather() {
        whenever(
            locationRepository.getLocation(
                locationCaptor.capture(),
                throwableCaptor.capture()
            )
        ).then { }

        whenever(
            weatherRepository.getWeather(
                any(),
                weatherCaptorMock.capture(),
                throwableCaptorMock.capture()
            )
        ).then { }

        viewModel.fetchWeather()

        val location = Location(LocationManager.GPS_PROVIDER)
        val weatherDto = WeatherDto(20, "City", ArrayList())

        locationCaptor.lastValue.invoke(location)
        weatherCaptorMock.lastValue.invoke(weatherDto)


        Assert.assertEquals(weatherDto, viewModel.weatherLiveData.value)
        Assert.assertFalse(viewModel.isLoading.value ?: true)
        Assert.assertFalse(viewModel.isError.value ?: true)
    }

    @Test
    fun fetchLocationFailure() {
        whenever(
            locationRepository.getLocation(
                locationCaptor.capture(),
                throwableCaptor.capture()
            )
        ).then { }

        viewModel.fetchWeather()

        throwableCaptor.lastValue.invoke(mock())

        Assert.assertFalse(viewModel.isLoading.value ?: true)
        Assert.assertTrue(viewModel.isError.value ?: false)
    }

    @Test
    fun fetchWeatherFailure() {
        whenever(
            locationRepository.getLocation(
                locationCaptor.capture(),
                throwableCaptor.capture()
            )
        ).then { }

        whenever(
            weatherRepository.getWeather(
                any(),
                weatherCaptorMock.capture(),
                throwableCaptorMock.capture()
            )
        ).then { }

        viewModel.fetchWeather()

        val location = Location(LocationManager.GPS_PROVIDER)

        locationCaptor.lastValue.invoke(location)
        throwableCaptorMock.lastValue.invoke(mock())

        Assert.assertFalse(viewModel.isLoading.value ?: true)
        Assert.assertTrue(viewModel.isError.value ?: false)
    }
}