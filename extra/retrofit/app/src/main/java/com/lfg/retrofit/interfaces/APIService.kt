package com.lfg.retrofit.interfaces

import com.lfg.retrofit.clases.DogsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getDogsByBreeds(@Url url:String): Response<DogsResponse>
}