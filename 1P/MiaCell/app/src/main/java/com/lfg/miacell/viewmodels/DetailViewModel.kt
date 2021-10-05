package com.lfg.miacell.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.lfg.miacell.R
import com.lfg.miacell.database.AppDatabase
import com.lfg.miacell.database.ProductDao
import com.lfg.miacell.entities.Product

class DetailViewModel : ViewModel() {
    private val PREF_NAME = "mySelection"
    private var db: AppDatabase? = null
    private var productDao: ProductDao? = null
    val product = MutableLiveData<Product>()
    val mode = MutableLiveData<String>()
    lateinit var newProd : Product

    fun onCreateDB (context : Context) {
        db = AppDatabase.getAppDataBase(context)
        productDao = db?.ProductDao()
    }

    fun onStartDetail (context : Context) {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val id : Long = sharedPref.getLong("id",0)
        setMode(sharedPref.getString("detailMode","view")!!)

        product.value = Product(8806090,"","",0.0,"","")
        if(id >= 0)
            setProductValueById(id)
    }

    private fun setProductValueById(id : Long)
    {
        val pr = productDao?.loadProductById(id)!!
        if (pr.id > 0)
            product.value = pr
    }

    fun setViewImage (context : Context, img : ImageView, id : String)
    {
        var strImage = if (id == "") {
            product.value!!.urlImage
        } else {
            "https://imagenes.preciosclaros.gob.ar/productos/${id}.jpg"
        }
        if (!URLUtil.isValidUrl(strImage))
            strImage = "https://www.preciosclaros.gob.ar/img/no-image.png"

        Glide
            .with(context)
            .load(strImage)
            .centerInside()
            .into(img)
    }

    fun setMode(str : String)
    {
        mode.value = str
    }
    fun deteleItem(view : View)
    {
        productDao?.delete(product.value)
        Snackbar.make(view, R.string.txt_delete_detail, Snackbar.LENGTH_SHORT).show()
    }
    fun editItem(view : View)
    {
        if(newProd.id>0)
        {
            productDao?.updateProduct(newProd)
            setProductValueById(newProd.id)
            Snackbar.make(view, R.string.txt_edit_detail, Snackbar.LENGTH_SHORT).show()
        }
    }
    fun addItem(view : View)
    {
        if(newProd.id>0)
        {
            productDao?.insertProduct(newProd)
            setProductValueById(newProd.id)
            Snackbar.make(view, R.string.txt_add_detail, Snackbar.LENGTH_SHORT).show()
        }
    }

    fun setProductData(id : String, brand : String, desc : String, price : String)
    {
        val strImage = "https://imagenes.preciosclaros.gob.ar/productos/${id}.jpg"
        newProd = try {
            Product(id.toLong(),brand,desc,price.toDouble(),"1.0 un", strImage)
        } catch (ex : Exception) {
            Product(0,"","",0.0,"","")
        }
    }
}