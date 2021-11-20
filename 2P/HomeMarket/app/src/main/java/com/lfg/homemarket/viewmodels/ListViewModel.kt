package com.lfg.homemarket.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.lfg.homemarket.clases.ItemResponse
import com.lfg.homemarket.clases.ItemRetrofit
import com.lfg.homemarket.clases.Product
import kotlinx.coroutines.tasks.await
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

class ListViewModel : ViewModel() {
    var productList : MutableList<Product> = mutableListOf()
    private val PREF_NAME = "mySelection"
    private val PREF_LOCATION = "myLocation"
    private val PREF_SCANNED = "myScannedId"
    var latitud = MutableLiveData<String>()
    var longitud = MutableLiveData<String>()
    val db = Firebase.firestore
    lateinit var retrofit: ItemRetrofit

    fun getProductData () : MutableList<Product> {
        return productList
    }

    fun getStoredCoordinates(context : Context) {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_LOCATION, Context.MODE_PRIVATE)
        latitud.value = sharedPref.getString("latitud","-34.5986333")   //UTN FRBA
        longitud.value = sharedPref.getString("longitud","-58.4199851")
    }

    fun loadScannedId(context : Context) {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_SCANNED, Context.MODE_PRIVATE)
        val scannedId = sharedPref.getString("id","").toString()
        val editor = sharedPref.edit()
        editor.putString("id","")
        editor.apply()
        if(scannedId!="")
            saveToDbId(scannedId)
    }

    private fun saveToDbId(id : String) {
        retrofit.searchByQuery("producto?id_producto=${id.lowercase(Locale.getDefault())}&lat=${latitud.value}&lng=${longitud.value}")
    }

    suspend fun getProductListFromCloud(): Boolean {
        var result = false
        productList.clear()
        //traer lista de datos
        db.collection("listaproductos")
//             .whereEqualTo("tipo", "PERRO")
//             .limit(20)
//             .orderBy("edad")
            .whereEqualTo("show", true)
            .get()
            .addOnSuccessListener { snapshot ->
                try {
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

    fun loadProductList() {
        productList.clear()
        //traer lista de datos
        db.collection("listaproductos")
//             .whereEqualTo("tipo", "PERRO")
//             .limit(20)
//             .orderBy("edad")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (prod in snapshot) {
                        try {
                            val pr  = prod.toObject<Product>()
                            productList.add(pr)
                        }
                        catch (ex: Exception) {
                            Log.w("DB", "Error getting documents: ", ex)
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("DB", "Error getting documents: ", exception)
            }

    }

    fun onStartLoadId (context : Context) : String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString("id","")
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

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    fun stringFromDouble(db : Double, num : Int) : String =
        BigDecimal(db).setScale(num, RoundingMode.HALF_EVEN).toString()

    fun saveProductToDB(id : String, pr1 : ItemResponse, pr2 : Product) {
        val date = getCurrentDateTime()
        var idStructure = date.toString("yyyyMMdd")
        idStructure = "${id},${idStructure},${stringFromDouble(latitud.value!!.toDouble(),5)},${stringFromDouble(longitud.value!!.toDouble(),5)}"
        db.collection("preciosclaros").document(idStructure).set(pr1)
        db.collection("listaproductos").document(id).set(pr2)

    }
}