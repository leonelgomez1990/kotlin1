package com.lfg.homemarket.viewmodels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lfg.homemarket.clases.CameraHelper

class ScanViewModel : ViewModel() {
    lateinit var cameraHelper: CameraHelper
    private val PREF_NAME = "mySelection"
    var flashState : Boolean = false
    var scannedId : MutableLiveData<String> = MutableLiveData<String>()

    fun saveScannedId (context : Context, id : String)
    {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("id",id)
        editor.apply()
    }

}