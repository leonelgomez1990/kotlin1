package com.lfg.homemarket.clases

import com.lfg.homemarket.interfases.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemRetrofit (
    private val baseUrl : String,
    val onResponse : (Response<ItemResponse>) -> Unit
){
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun searchByQuery(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getItemsByQuery(query)
            onResponse(call)
        }
    }
}