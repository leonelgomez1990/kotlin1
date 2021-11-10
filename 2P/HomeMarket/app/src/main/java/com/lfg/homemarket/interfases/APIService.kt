package com.lfg.homemarket.interfases

import com.lfg.homemarket.clases.ItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getItemsByQuery(@Url url:String): Response<ItemResponse>
}