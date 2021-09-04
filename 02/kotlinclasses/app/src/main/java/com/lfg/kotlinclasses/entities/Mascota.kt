package com.lfg.kotlinclasses.entities

class Mascota (var nombre : String, var tipo : String, var raza : String, var edad : Int) {

    fun calcularEdad (edad : Int) : Int = edad*7

    class Constants {
        companion object {
            val typeGato = "GATO"
            val typePerro = "PERRO"
        }
    }

    override fun toString(): String = "Mascota(nombre='$nombre', tipo='$tipo', raza='$raza', edad=$edad)"
}