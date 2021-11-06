package clases

import enum.ColorElectrodomestico
import enum.TipoElectrodomestico

/*
Clase de Lavadora, hereda de Electrodomestico
parámetros: numeroSerie, color, peso y precio, capacidad
 */
class Lavadora (
    numeroSerie : String,
    color : ColorElectrodomestico,
    peso : Double,
    precio : Double,
    var capacidad : Double
        ) : Electrodomestico(numeroSerie, TipoElectrodomestico.LAVADORA, color, peso, precio) {

    override fun toString(): String {
        return super.toString() + " - Capacidad: $capacidad kg"
    }
}