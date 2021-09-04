package com.lfg.kotlinclasses.entities

class Cuadrado (var lado : Double) : Figura("",0.0,0.0) {

    override fun calcularPerimetro(): Double {
        return lado*4
    }

    override fun calcularSuperficie(): Double {
        return lado*lado
    }
}