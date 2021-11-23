package com.lfg.homemarket.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.util.Log
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.Source
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
    val price = MutableLiveData<Double>()
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
                    if (pr?.id!! > 0) {
                        product.value = pr!!
                        price.value = pr.price?:0.0
                    }
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
        var strImage = if (id == "")
            product.value!!.urlImage
        else
            PreciosClarosServer.getProductImageUrl(id)

        try {
            GlideApp
                .with(context)
                .load(strImage)
                .placeholder(R.mipmap.ic_no_picture)
                .centerInside()
                .listener(object : RequestListener<Drawable> {

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d("GLIDEAPP", "Error al obtener la carga de la foto $strImage")
                        return false
                    }
                })
                .into(img)
        }
        catch (ex : Exception) {}
    }

    private fun searchProductData(id : String) {
        retrofit.searchByQuery(PreciosClarosServer.getQuery(id))
    }

    suspend fun getBranchListFromCloud(): String {
        var result = ""
        val id = product.value!!.id
        val idStructure = ProductIdStructure.getFromId(id.toString())
        // Source can be CACHE, SERVER, or DEFAULT.
        val source = Source.DEFAULT
        //traer lista de datos
        db.collection("preciosclaros").document(idStructure)
            .get(source)
            .addOnSuccessListener { snapshot ->
                try {
                    branchList.clear()
                    val itr  = snapshot.toObject<ItemResponse>()
                    if(itr == null) {
                        searchProductData(id.toString())
                        result = "RETROFIT"
                    }
                    else {
                        var lowPrice = 0.0
                        for (suc in itr?.sucursales!!) {
                            val price = if (suc.sucursalTipo == "Mayorista")
                                suc.preciosProducto.precio_unitario_con_iva.toDouble()
                            else
                                suc.preciosProducto.precioLista.toDouble()
                            val urlImage = PreciosClarosServer.getBranchImageUrl(suc.comercioId, suc.banderaId)
                            if((lowPrice == 0.0) || (lowPrice > price))
                                lowPrice = price

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
                            checkNewProductValue(lowPrice)
                        }
                        result = "OK"
                    }
                }
                catch (ex: Exception) {
                    Log.w("DB", "Error getting documents: ", ex)
                    result = "Error getting documents: " + ex.message
                }
            }
            .addOnFailureListener { exception ->
                Log.w("DB", "Error getting documents: ", exception)
                result = "Error getting documents: " + exception.message
            }
            .await()


        return result
    }

    fun saveTodayDataToDB(id : String, pr1 : ItemResponse) {
        val idStructure = ProductIdStructure.getFromId(id)
        db.collection("preciosclaros").document(idStructure).set(pr1)
    }

    fun checkNewProductValue(newVal : Double) {
        if(product.value?.id?:0  > 0) {
            val id = product.value?.id.toString()
            if(product.value!!.price != newVal) {
                product.value!!.price = newVal
                price.value = newVal
                db.collection("listaproductos").document(id).update("price" , newVal)
            }
        }
    }

}
