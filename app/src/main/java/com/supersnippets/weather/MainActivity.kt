package com.supersnippets.weather

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.supersnippets.weather.databinding.ActivityMainBinding
import com.supersnippets.weather.viewmodels.WeatherViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        viewModel.getIsLoading().observe(this, Observer {
            println("loading changed $it")
            if (it) showProgressBar() else hideProgressBar()
        })

        viewModel.getWeather().observe(this, Observer {
            println("weather changed $it")
        })

        viewModel.fetchWeather()
    }

    private fun showProgressBar() {
        binding.loader.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.loader.visibility = View.GONE
    }
}