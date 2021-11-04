package com.lfg.retrofit.viewmodels

import androidx.lifecycle.ViewModel
import com.lfg.retrofit.clases.Product

class ListViewModel : ViewModel() {
    var productList : MutableList<Product> = mutableListOf()

    fun getProductData () : MutableList<Product> {
        return productList
    }
}