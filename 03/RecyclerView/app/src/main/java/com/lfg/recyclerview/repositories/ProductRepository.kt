package com.lfg.recyclerview.repositories

import com.lfg.recyclerview.entities.Product

class ProductRepository {

    private var productList : MutableList<Product> = mutableListOf()

    init {
        productList.add(Product("7794626009235","HUGGIES","Pañal Talle XG Mega Classic Huggies 18 Un", 418.9,"18.0 un","https://imagenes.preciosclaros.gob.ar/productos/7794626009235.jpg"))
        productList.add(Product("7794626009211","HUGGIES","Pañal M Huggies Classic Plus Mega 26 Un", 389.0,"32.0 un","https://imagenes.preciosclaros.gob.ar/productos/7794626009211.jpg"))
        productList.add(Product("7794626009228","HUGGIES","Pañal G Mega Classic Huggies 22 Un", 418.9,"22.0 un","https://imagenes.preciosclaros.gob.ar/productos/7794626009228.jpg"))
        productList.add(Product("7794626009242","HUGGIES","Pañal XXG Huggies Mega Classic 17 Un", 389.0,"17.0 un","https://imagenes.preciosclaros.gob.ar/productos/7794626009242.jpg"))
        productList.add(Product("7794626999888","HUGGIES","Pañales Triple Proteccion Recien Nacido Huggies 34 Un", 419.13,"34.0 un","https://imagenes.preciosclaros.gob.ar/productos/7794626999888.jpg"))
        productList.add(Product("7500435133357","PAMPERS","Pañal Supersec Pampers 30 Un", 459.4,"30.0 un","https://imagenes.preciosclaros.gob.ar/productos/7500435133357.jpg"))
        productList.add(Product("7500435169103","PAMPERS","Pañal Talle Recien Nacido Confort Sec Ultra Pampers 36 Un", 816.5,"36.0 un","https://imagenes.preciosclaros.gob.ar/productos/7500435169103.jpg"))
    }
    fun getList() : MutableList<Product> {
        return productList
    }
}
