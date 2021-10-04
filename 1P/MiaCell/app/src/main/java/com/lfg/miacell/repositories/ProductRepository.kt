package com.lfg.miacell.repositories

import com.lfg.miacell.entities.Product

class ProductRepository {

    private var productList : MutableList<Product> = mutableListOf()

    init {
        productList.add(Product(8806090693632,"SAMSUNG","Celular Samsung A01 Core Azul 1 Un", 15499.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090693632.jpg"))
        productList.add(Product(8806090039782,"SAMSUNG","Celular Samsung Galaxy A10S Negro 1 Un", 21999.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090039782.jpg"))
        productList.add(Product(8806090197888,"SAMSUNG","Celular Samsung Galaxy A51 Negro 1 Un", 47999.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090197888.jpg"))
        productList.add(Product(8806090265112,"SAMSUNG","Celular Samsung Galaxy A51 Blanco 1 Un", 47999.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090265112.jpg"))
        productList.add(Product(8806090283666,"SAMSUNG","Celular Samsung Galaxy A71 Plata 1 Un", 63999.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090283666.jpg"))
        productList.add(Product(8806090283710,"SAMSUNG","Celular Samsung Galaxy A71 Negro 1 Un", 63999.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090283710.jpg"))
        productList.add(Product(8806090361432,"SAMSUNG","Celular Samsung A01 Negro 1 Un", 19999.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090361432.jpg"))
        productList.add(Product(8806090361593,"SAMSUNG","Celular Samsung A01 Azul 1 Un", 19999.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090361593.jpg"))
        productList.add(Product(8806090448478,"SAMSUNG","Celular Samsung A31 Negro 1 Un", 39999.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090448478.jpg"))
        productList.add(Product(8806090448591,"SAMSUNG","Celular Samsung A31 Azul 1 Un", 39999.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090448591.jpg"))
        productList.add(Product(8806090448652,"SAMSUNG","Celular Samsung A31 Blanco 1 Un", 39999.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090448652.jpg"))
        productList.add(Product(8806090548345,"SAMSUNG","Celular Samsung Galaxy A11 Blanco 1 Un", 24499.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090548345.jpg"))
        productList.add(Product(8806090548451,"SAMSUNG","Celular Samsung Galaxy A11 Negro 1 Un", 24499.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090548451.jpg"))
        productList.add(Product(8806090548611,"SAMSUNG","Celular Samsung Galaxy A11 Azul 1 Un", 24499.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090548611.jpg"))
        productList.add(Product(8806090579820,"SAMSUNG","Celular Samsung Galaxy A21 Negro1 Un", 30999.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090579820.jpg"))
        productList.add(Product(8806090579851,"SAMSUNG","Celular Samsung Galaxy A21 Blanco 1 Un", 30999.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090579851.jpg"))
        productList.add(Product(8806090657849,"SAMSUNG","Celular Libre Samsung Galaxy Note 20 Gris 8 256Gb 1 Un", 120999.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090657849.jpg"))
        productList.add(Product(8806090693601,"SAMSUNG","Celular Samsung A01 Core Negro 1 Un", 15499.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090693601.jpg"))
        productList.add(Product(8806090751769,"SAMSUNG","Celular Samsung S20 Fe 6/128G 6.5 1 Un", 84999.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090751769.jpg"))
        productList.add(Product(8806090751776,"SAMSUNG","Celular Libre Samsung Galaxy S20 Fe Lavanda 6 128G 1 Un", 84999.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090751776.jpg"))
        productList.add(Product(8806090787867,"SAMSUNG","Celular Libre Samsung Galaxy S20 Fe Rojo 6 128Gb 1 Un", 84999.0,"1.0 un","https://imagenes.preciosclaros.gob.ar/productos/8806090787867.jpg"))
    }
    fun getList() : MutableList<Product> {
        return productList
    }
}