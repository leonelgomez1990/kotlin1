package com.lfg.homemarket.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.lfg.homemarket.clases.*
import kotlinx.coroutines.tasks.await
import java.util.*

class ListViewModel : ViewModel() {
    var productList : MutableList<Product> = mutableListOf()
    private val PREF_NAME = "mySelection"
    private val PREF_SCANNED = "myScannedId"
    private val db = Firebase.firestore
    lateinit var retrofit: ItemRetrofit

    fun getItemData () : MutableList<Product> = productList

    fun loadScannedId(context : Context) {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_SCANNED, Context.MODE_PRIVATE)
        val scannedId = sharedPref.getString("id","").toString()
        val editor = sharedPref.edit()
        editor.putString("id","")
        editor.apply()
        if(scannedId!="")
            searchProductData(scannedId)
    }

    private fun searchProductData(id : String) {
        retrofit.searchByQuery(PreciosClarosServer.getQuery(id))
    }

    suspend fun getProductListFromCloud(): Boolean {
        var result = false
        // Source can be CACHE, SERVER, or DEFAULT.
        val source = Source.DEFAULT
        //traer lista de datos
        db.collection("listaproductos")
            .whereEqualTo("show", true)
            .get(source)
            .addOnSuccessListener { snapshot ->
                try {
                    productList.clear()
                    productList.addAll(snapshot.toObjects())
                    result = true
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

    fun snapshotListener(onActionResult : (Unit) -> Unit) {
        val docRef = db.collection("listaproductos").whereEqualTo("show", true)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.d("DB", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                try {
                    productList.clear()
                    productList.addAll(snapshot.toObjects())
                    onActionResult(Unit)
                }
                catch (ex: Exception) {
                    Log.w("DB", "Error getting documents: ", ex)
                }
            } else {
                Log.d("DB", "Current data: null")
            }
        }


    }

    fun saveDetailData (context : Context, pos : Int)
    {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("position",pos)
        var id : Long = -1
        if (pos != -1)
            id = productList[pos].id
        editor.putLong("id",id)

        editor.apply()
    }

    fun saveProductToDB(id : String, pr1 : ItemResponse, pr2 : Product) {
        val idStructure = ProductIdStructure.getFromId(id)
        db.collection("preciosclaros").document(idStructure).set(pr1)
        db.collection("listaproductos").document(id).set(pr2)

    }

    fun deleteProductInDB(id : String) {
        db.collection("listaproductos").document(id).update("show" , false)
    }
}