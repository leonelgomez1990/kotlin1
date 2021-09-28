package com.lfg.almacenleo.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User (
    id : Int,
    user : String,
    display : String,
    password : String
) {
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id : Int

    @ColumnInfo(name = "user")
    var user : String

    @ColumnInfo(name = "display")
    var display : String

    @ColumnInfo(name = "password")
    var password : String

    init {
        this.id = id
        this.user = user
        this.display = display
        this.password = password
    }
}