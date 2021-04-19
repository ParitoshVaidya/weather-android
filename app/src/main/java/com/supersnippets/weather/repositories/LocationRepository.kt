package com.supersnippets.weather.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Criteria
import android.location.Location
import android.location.LocationManager

class LocationRepository(context: Context) {
    private val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager?

    @SuppressLint("MissingPermission")
    fun getLocation(onSuccess: (Location) -> Unit, onFailure: (Throwable?) -> Unit) {
        val criteria = Criteria()
        val bestProvider = locationManager!!.getBestProvider(criteria, false)
        val location = locationManager.getLastKnownLocation(bestProvider!!)
        try {
            location?.let { onSuccess(it) }
        } catch (e: NullPointerException) {
            onFailure(e)
        }
    }
}