package com.lfg.retrofit.clases

class Product (
    id : Long,
    brand : String,
    description : String,
    price : Double,
    presentation : String,
    urlImage : String
) {
    var id : Long

    var brand : String

    var description : String

    var price : Double

    var presentation : String

    var urlImage : String

    init {
        this.id = id
        this.brand = brand
        this.description = description
        this.price = price
        this.presentation = presentation
        this.urlImage = urlImage
    }
}