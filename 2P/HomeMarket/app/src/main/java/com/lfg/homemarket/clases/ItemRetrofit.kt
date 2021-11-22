package com.lfg.homemarket.clases

import android.content.Context
import android.widget.Toast
import com.lfg.homemarket.interfases.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.lfg.homemarket.webservice.NetworkConnectionInterceptor
import okhttp3.OkHttpClient

class ItemRetrofit (
    private val baseUrl : String,
    val mContext : Context,
    val onResponse : (Response<ItemResponse>?) -> Unit
){
    private fun getRetrofit(): Retrofit {
        val oktHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor(NetworkConnectionInterceptor(mContext))
        // Adding NetworkConnectionInterceptor with okHttpClientBuilder.

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(oktHttpClient.build())
            .build()
    }

    fun searchByQuery(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = getRetrofit().create(APIService::class.java).getItemsByQuery(query)
                onResponse(call)
            }
            catch (ex: Exception) {
                lastErrorMessage = ex.message.toString()
                onResponse(null)
            }
        }
    }

    companion object {
        var lastErrorMessage = ""
    }
}