package com.lfg.miacell.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
class Product (
    id : Long,
    brand : String,
    description : String,
    price : Double,
    presentation : String,
    urlImage : String
) {
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id : Long

    @ColumnInfo(name = "brand")
    var brand : String

    @ColumnInfo(name = "description")
    var description : String

    @ColumnInfo(name = "price")
    var price : Double

    @ColumnInfo(name = "presentation")
    var presentation : String

    @ColumnInfo(name = "urlImage")
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