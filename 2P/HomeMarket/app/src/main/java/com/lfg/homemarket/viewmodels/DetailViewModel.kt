package com.lfg.homemarket.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.lfg.homemarket.clases.Product

class DetailViewModel : ViewModel() {
    private val PREF_NAME = "mySelection"
    val product = MutableLiveData<Product>()
    val db = Firebase.firestore

    fun onStartDetail (context : Context) {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val id : Long = sharedPref.getLong("id",0)

        product.value = Product(0,"","",0.0,"","")
        if(id >= 0)
            setProductValueById(id)
    }

    private fun setProductValueById(id : Long)
    {
        val docRef = db.collection("listaproductos").document(id.toString())
        docRef.get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot != null) {
                    val pr  = dataSnapshot.toObject<Product>()
                    Log.d("Test", "DocumentSnapshot data: ${pr.toString()}")
                    if (pr?.id!! > 0)
                        product.value = pr!!

                } else {
                    Log.d("Test", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Test", "get failed with ", exception)
            }

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

    fun setProductData(id : String, brand : String, desc : String, price : String)
    {
        val strImage = "https://imagenes.preciosclaros.gob.ar/productos/${id}.jpg"
        val newProd = try {
            Product(id.toLong(),brand,desc,price.toDouble(),"1.0 un", strImage)
        } catch (ex : Exception) {
            Product(0,"","",0.0,"","")
        }
    }
}