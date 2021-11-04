package com.lfg.retrofit.clases

import com.google.gson.annotations.SerializedName

data class ItemResponse(
    @SerializedName("status") val status : Int,
    @SerializedName("total") val total : Int,
    @SerializedName("producto") val producto : Producto,
    @SerializedName("maxLimitPermitido") val maxLimitPermitido : Int,
    @SerializedName("totalPagina") val totalPagina : Int,
    @SerializedName("sucursales") val sucursales : List<Sucursales>
)

data class Producto (
    @SerializedName("msg") val msg : String?,
    @SerializedName("presentacion") val presentacion : String,
    @SerializedName("nombre") val nombre : String,
    @SerializedName("id") val id : String,
    @SerializedName("marca") val marca : String
)

data class Sucursales (

    @SerializedName("distanciaNumero") val distanciaNumero : Double,
    @SerializedName("distanciaDescripcion") val distanciaDescripcion : String,
    @SerializedName("banderaId") val banderaId : Int,
    @SerializedName("unidad_venta") val unidad_venta : String,
    @SerializedName("lat") val lat : Double,
    @SerializedName("lng") val lng : Double,
    @SerializedName("sucursalNombre") val sucursalNombre : String,
    @SerializedName("id") val id : Int,
    @SerializedName("sucursalTipo") val sucursalTipo : String,
    @SerializedName("provincia") val provincia : String,
    @SerializedName("preciosProducto") val preciosProducto : PreciosProducto,
    @SerializedName("actualizadoHoy") val actualizadoHoy : Boolean,
    @SerializedName("direccion") val direccion : String,
    @SerializedName("banderaDescripcion") val banderaDescripcion : String,
    @SerializedName("localidad") val localidad : String,
    @SerializedName("comercioRazonSocial") val comercioRazonSocial : String,
    @SerializedName("comercioId") val comercioId : Int
)

data class PreciosProducto (

    @SerializedName("promo1") val promo1 : Promo1,
    @SerializedName("precio_unitario_con_iva") val precio_unitario_con_iva : String,
    @SerializedName("precioLista") val precioLista : String,
    @SerializedName("precio_unitario_sin_iva") val precio_unitario_sin_iva : String,
    @SerializedName("promo2") val promo2 : Promo2,
    @SerializedName("precio_bulto_sin_iva") val precio_bulto_sin_iva : String,
    @SerializedName("precio_bulto_con_iva") val precio_bulto_con_iva : String
)

data class Promo1 (

    @SerializedName("descripcion") val descripcion : String,
    @SerializedName("precio_unitario_sin_iva") val precio_unitario_sin_iva : String,
    @SerializedName("precio_unitario_con_iva") val precio_unitario_con_iva : String,
    @SerializedName("precio") val precio : String
)

data class Promo2 (

    @SerializedName("descripcion") val descripcion : String,
    @SerializedName("precio_unitario_sin_iva") val precio_unitario_sin_iva : String,
    @SerializedName("precio_unitario_con_iva") val precio_unitario_con_iva : String,
    @SerializedName("precio") val precio : String
)
