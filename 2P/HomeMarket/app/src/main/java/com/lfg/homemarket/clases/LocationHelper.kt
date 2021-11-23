package com.lfg.homemarket.clases

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*


class LocationHelper (
    var activity : AppCompatActivity,
    var context : Context,
    val onGettingLocation : (String, String) -> Unit
        ) {
    var mFusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

    init {
        getLastKnownLocation()
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() : Boolean {
        checkPermissions()
        if(allPermissionsGranted()) {
            return try {
                mFusedLocationClient.lastLocation
                    .addOnCompleteListener(activity) { task ->
                        val location: Location? = task.result
                        if (location == null) {
                            requestNewLocationData()
                        } else {
                            Log.d (TAG,location.latitude.toString())
                            Log.d (TAG,location.longitude.toString())
                            onGettingLocation(location.latitude.toString(),location.longitude.toString())
                        }
                    }
                true
            } catch (ex : Exception) {
                false
            }
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            activity,
            REQUIRED_PERMISSIONS,
            REQUEST_CODE_PERMISSIONS
        )
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    context, permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 360000

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location: Location = locationResult.lastLocation
            if (location != null) {
                Log.d (TAG,location.latitude.toString())
                Log.d (TAG,location.longitude.toString())
                onGettingLocation(location.latitude.toString(),location.longitude.toString())
            }
        }
    }

    companion object {
        const val TAG = "LocationHelper"
        const val REQUEST_CODE_PERMISSIONS = 42
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    }

}