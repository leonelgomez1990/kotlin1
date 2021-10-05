package com.lfg.miacell.viewmodels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.lfg.miacell.database.AppDatabase
import com.lfg.miacell.database.ProductDao
import com.lfg.miacell.entities.Product
import com.lfg.miacell.repositories.ProductRepository

class ListViewModel : ViewModel() {
    private var db: AppDatabase? = null
    private var productDao: ProductDao? = null
    private var productRepository = ProductRepository()
    private var productList : MutableList<Product> = mutableListOf()
    private val PREF_NAME = "mySelection"

    fun onCreateDB (context : Context) {
        db = AppDatabase.getAppDataBase(context)
        productDao = db?.ProductDao()
        productList = productDao?.loadAllProducts()!!
        /*if (productList.size == 0)
        {
            for (product in productRepository.getList()){
                insertProduct(product)
            }
            productList = productDao?.loadAllProducts()!!
        }*/
    }

    fun getProductData () : MutableList<Product> {
        productList = productDao?.loadAllProducts()!!
        return productList
    }

    //fun insertProduct (id : Long, brand : String, description : String, price : Double, presentation : String, urlImage : String) : Boolean
    private fun insertProduct (p : Product) : Boolean
    {
        return if (productDao?.loadProductById(p.id) == null) {
            productDao?.insertProduct(Product(p.id, p.brand, p.description, p.price, p.presentation, p.urlImage))
            true
        } else
            false
    }

    fun saveDetailData (context : Context, pos : Int, mode : String)
    {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("position",pos)
        var id : Long = -1
        if (pos != -1)
            id = productList[pos].id
        editor.putLong("id",id)
        editor.putString("detailMode",mode)

        editor.apply()
    }

}