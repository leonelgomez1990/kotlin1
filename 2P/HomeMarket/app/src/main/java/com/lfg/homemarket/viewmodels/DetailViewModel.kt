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
import com.lfg.homemarket.R
import com.lfg.homemarket.clases.*
import kotlinx.coroutines.tasks.await

class DetailViewModel : ViewModel() {
    var branchList : MutableList<PriceBranch> = mutableListOf()
    private val PREF_NAME = "mySelection"
    val product = MutableLiveData<Product>()
    private val db = Firebase.firestore
    lateinit var retrofit: ItemRetrofit

    fun getItemData () : MutableList<PriceBranch> = branchList

    fun onStartDetail (context : Context) {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val id : Long = sharedPref.getLong("id",0)

        product.value = Product(0,"","",0.0,"",true,"")
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
            .placeholder(R.mipmap.ic_no_picture)
            .centerInside()
            .into(img)
    }

    private fun searchProductData(id : String) {
        retrofit.searchByQuery(PreciosClarosServer.getQuery(id))
    }

    suspend fun getBranchListFromCloud(): Boolean {
        var result = false
        val id = product.value!!.id
        val idStructure = ProductIdStructure.getFromId(id.toString())
        //traer lista de datos
        db.collection("preciosclaros").document(idStructure)
            .get()
            .addOnSuccessListener { snapshot ->
                try {
                    branchList.clear()
                    val itr  = snapshot.toObject<ItemResponse>()
                    if(itr == null) {
                        searchProductData(id.toString())
                    }
                    else {
                        for (suc in itr?.sucursales!!) {
                            val price = if (suc.sucursalTipo == "Mayorista")
                                suc.preciosProducto.precio_unitario_con_iva.toDouble()
                            else
                                suc.preciosProducto.precioLista.toDouble()
                            val urlImage = "https://imagenes.preciosclaros.gob.ar/comercios/${suc.comercioId}-${suc.banderaId}.jpg"

                            val branch = PriceBranch(
                                id,
                                suc.sucursalTipo,
                                suc.banderaDescripcion,
                                price,
                                suc.direccion,
                                suc.distanciaNumero,
                                urlImage
                            )
                            branchList.add(branch)
                        }
                        result = true
                    }
                }
                catch (ex: Exception) {
                    Log.w("DB", "Error getting documents: ", ex)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("DB", "Error getting documents: ", exception)
            }
            .await()


        return result
    }

    fun saveTodayDataToDB(id : String, pr1 : ItemResponse) {
        val idStructure = ProductIdStructure.getFromId(id)
        db.collection("preciosclaros").document(idStructure).set(pr1)
    }

}
