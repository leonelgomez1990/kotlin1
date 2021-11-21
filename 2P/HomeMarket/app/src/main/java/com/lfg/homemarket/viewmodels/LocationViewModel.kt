package com.lfg.homemarket.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lfg.homemarket.clases.LocationCoordinates
import java.lang.Double.parseDouble

class LocationViewModel : ViewModel() {
    var latitud = MutableLiveData<String>()
    var longitud = MutableLiveData<String>()

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
        LocationCoordinates.latitud = latitud.value.toString()
        LocationCoordinates.longitud = longitud.value.toString()
        LocationCoordinates.setStoredCoordinates(context)
    }

    fun getStoredCoordinates(context : Context) {
        LocationCoordinates.getStoredCoordinates(context)
        latitud.value = LocationCoordinates.latitud
        longitud.value = LocationCoordinates.longitud
    }

}