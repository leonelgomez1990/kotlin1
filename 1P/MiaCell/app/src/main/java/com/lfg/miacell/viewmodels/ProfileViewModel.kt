package com.lfg.miacell.viewmodels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val PREF_NAME = "myUser"
    lateinit var sharedPref: SharedPreferences
    val user = MutableLiveData<String>()
    val display = MutableLiveData<String>()


    fun onStartProfile (context : Context) {
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        user.value = sharedPref.getString("USER","default")!!
        display.value = sharedPref.getString("DISPLAY","default")!!
    }

    fun setAutoLoginFalse() {
        val editor = sharedPref.edit()
        editor.putBoolean("AUTOLOGIN", false)
        editor.apply()
    }
}