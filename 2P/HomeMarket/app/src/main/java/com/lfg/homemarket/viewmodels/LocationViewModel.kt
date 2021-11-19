package com.lfg.homemarket.viewmodels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {
    var latitud = MutableLiveData<String>()
    var longitud = MutableLiveData<String>()
    private val PREF_LOCATION = "myLocation"

    fun askForLocation(lat : String, long : String) : Boolean{
        latitud.value = lat
        longitud.value = long
        return true
    }

    fun setCoordinates(context : Context, lat : String, long : String) : Boolean{
        latitud.value = lat
        longitud.value = long
        //Verificar si las coordenadas se pueden guardar

        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_LOCATION, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("latitud",lat)
        editor.putString("longitud",long)
        editor.apply()
        return true
    }

    fun getStoredCoordinates(context : Context) {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_LOCATION, Context.MODE_PRIVATE)
        latitud.value = sharedPref.getString("latitud","-34.713078")
        longitud.value = sharedPref.getString("longitud","-58.497269")
    }

}