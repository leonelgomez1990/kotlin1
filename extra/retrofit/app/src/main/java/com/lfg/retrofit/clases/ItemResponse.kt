package com.lfg.retrofit.clases

import com.google.gson.annotations.SerializedName

data class ItemResponse(
    @SerializedName("status") var status: String,
    @SerializedName("message") var images: List<String>
)