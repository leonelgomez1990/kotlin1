package com.lfg.homemarket.clases

import java.util.*

class PreciosClarosServer {
    companion object {
        const val BASE_URL = "https://d3e6htiiul5ek9.cloudfront.net/prod/"
        const val PRODUCT_URL = "https://imagenes.preciosclaros.gob.ar/productos/"
        const val BRANCH_URL = "gs://home-market-82694.appspot.com/comercios/"

        fun getQuery(id : String) : String {
            return "producto?id_producto=${id.lowercase(Locale.getDefault())}&lat=${LocationCoordinates.latitud}&lng=${LocationCoordinates.longitud}"
        }

        fun getProductImageUrl(id : String) : String{
            return "$PRODUCT_URL${id}.jpg"
        }

        fun getProductImageUrl(id : Long) : String{
            return "$PRODUCT_URL${id}.jpg"
        }

        fun getBranchImageUrl(flag1 : Int, flag2 : Int) : String {
            return "$BRANCH_URL$flag1-$flag2.jpg"
        }
    }
}