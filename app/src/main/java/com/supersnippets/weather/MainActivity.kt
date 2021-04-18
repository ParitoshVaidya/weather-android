package com.supersnippets.weather

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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

        val anim: Animation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim)
        binding.imgLoader.startAnimation(anim)

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
        binding.imgLoader.visibility = View.VISIBLE
        binding.layoutTemp.visibility = View.GONE
        binding.layoutForecast.visibility = View.GONE
    }

    private fun hideProgressBar() {
        binding.imgLoader.visibility = View.GONE
        binding.layoutTemp.visibility = View.VISIBLE
        binding.layoutForecast.visibility = View.VISIBLE
    }
}