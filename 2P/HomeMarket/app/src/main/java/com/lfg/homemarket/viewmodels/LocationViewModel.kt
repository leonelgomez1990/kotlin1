package com.lfg.homemarket.viewmodels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Double.parseDouble

class LocationViewModel : ViewModel() {
    var latitud = MutableLiveData<String>()
    var longitud = MutableLiveData<String>()
    private val PREF_LOCATION = "myLocation"

    fun saveLocationValue(lat : String, long : String) : Boolean{
        //Verificar si las coordenadas se pueden guardar
        try {
            val nlat = parseDouble(lat)
            val nlong = parseDouble(long)
        } catch (e: NumberFormatException) {
            return false
        }

        latitud.value = lat
        longitud.value = long
        return true
    }

    fun setStoredCoordinates(context : Context) {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_LOCATION, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("latitud",latitud.value)
        editor.putString("longitud",longitud.value)
        editor.apply()
    }

    fun getStoredCoordinates(context : Context) {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_LOCATION, Context.MODE_PRIVATE)
        latitud.value = sharedPref.getString("latitud","-34.5986333")   //UTN FRBA
        longitud.value = sharedPref.getString("longitud","-58.4199851")
    }

}