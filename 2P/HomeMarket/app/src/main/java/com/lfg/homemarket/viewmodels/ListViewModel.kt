package com.lfg.homemarket.viewmodels

import androidx.lifecycle.ViewModel
import com.lfg.homemarket.clases.Product

class ListViewModel : ViewModel() {
    var productList : MutableList<Product> = mutableListOf()

    fun getProductData () : MutableList<Product> {
        return productList
    }
}