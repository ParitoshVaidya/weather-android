package com.supersnippets.weather

import android.Manifest
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.supersnippets.weather.databinding.ActivityMainBinding
import com.supersnippets.weather.databinding.LayoutErrorBinding
import com.supersnippets.weather.databinding.LayoutTempBinding
import com.supersnippets.weather.viewmodels.WeatherViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), PermissionListener {
    private val weatherViewModel by viewModel<WeatherViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var layoutTempBinding: LayoutTempBinding
    private lateinit var layoutErrorBinding: LayoutErrorBinding
    private lateinit var anim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        layoutTempBinding = LayoutTempBinding.bind(view)
        layoutErrorBinding = LayoutErrorBinding.bind(view)
        setContentView(view)
        anim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim)

        checkPermissions()

        layoutErrorBinding.btnRetry.setOnClickListener {
            checkPermissions()
        }
    }

    private fun checkPermissions() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            .withListener(this)
            .check()
    }

    private fun showProgressBar() {
        binding.imgLoader.visibility = View.VISIBLE
        binding.imgLoader.startAnimation(anim)
        binding.includeLayoutTemp.layoutTemp.visibility = View.GONE
        binding.includeLayoutError.layoutError.visibility = View.GONE
    }

    private fun hideProgressBar() {
        binding.imgLoader.clearAnimation()
        binding.imgLoader.visibility = View.GONE
        binding.includeLayoutTemp.layoutTemp.visibility = View.VISIBLE
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        weatherViewModel.fetchWeather()

        weatherViewModel.isLoading.observe(this, Observer {
            println("loading changed $it")
            if (it) showProgressBar() else hideProgressBar()
        })

        weatherViewModel.isError.observe(this, Observer {
            println("loading changed $it")
            if (it) {
                showErrorLayout()
            }
        })

        weatherViewModel.weatherLiveData.observe(this, Observer {
            println("weather changed $it")
            layoutTempBinding.txtTemp.text = it.temperature.toString() + "°"
            layoutTempBinding.txtCity.text = it.city
        })
    }

    private fun showErrorLayout() {
        binding.includeLayoutTemp.layoutTemp.visibility = View.GONE
        binding.includeLayoutError.layoutError.visibility = View.VISIBLE
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        showErrorLayout()
    }

    override fun onPermissionRationaleShouldBeShown(
        request: PermissionRequest?,
        token: PermissionToken?
    ) {
        token?.continuePermissionRequest();
    }
}