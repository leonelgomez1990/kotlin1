package com.lfg.homemarket.clases

import android.content.Context
import android.content.SharedPreferences

class LocationCoordinates {
    companion object {
        private const val PREF_LOCATION = "myLocation"
        private const val DEFAULT_LAT = "-34.5986333"
        private const val DEFAULT_LONG = "-58.4199851"
        var latitud : String = DEFAULT_LAT
        var longitud : String = DEFAULT_LONG

        fun setStoredCoordinates(context : Context) {
            val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_LOCATION, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("latitud",latitud)
            editor.putString("longitud",longitud)
            editor.apply()
        }

        fun getStoredCoordinates(context : Context) {
            val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_LOCATION, Context.MODE_PRIVATE)
            latitud = sharedPref.getString("latitud",DEFAULT_LAT) ?: DEFAULT_LAT
            longitud = sharedPref.getString("longitud",DEFAULT_LONG) ?: DEFAULT_LONG
        }
    }
}