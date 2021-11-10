package com.lfg.homemarket.viewmodels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.lfg.homemarket.clases.Product

class ListViewModel : ViewModel() {
    var productList : MutableList<Product> = mutableListOf()
    private val PREF_NAME = "mySelection"

    fun getProductData () : MutableList<Product> {
        return productList
    }

    fun onStartLoadId (context : Context) : String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString("id","")
    }

}