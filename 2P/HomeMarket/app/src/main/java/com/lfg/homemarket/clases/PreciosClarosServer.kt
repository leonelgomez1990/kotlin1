package com.lfg.homemarket.clases

import java.util.*

class PreciosClarosServer {
    companion object {
        const val BASE_URL = "https://d3e6htiiul5ek9.cloudfront.net/prod/"

        fun getQuery(id : String) : String {
            return "producto?id_producto=${id.lowercase(Locale.getDefault())}&lat=${LocationCoordinates.latitud}&lng=${LocationCoordinates.longitud}"
        }
    }
}