package clases

import enum.ColorElectrodomestico
import enum.TipoElectrodomestico

/*
Clase de Lavadora, hereda de Electrodomestico
parámetros: numeroSerie, color, peso y precio, potencia
 */
class Microondas (
    numeroSerie : String,
    color : ColorElectrodomestico,
    peso : Double,
    precio : Double,
    var potencia : Double
) : Electrodomestico(numeroSerie, TipoElectrodomestico.MICROONDAS, color, peso, precio) {

    override fun toString(): String {
        return super.toString() + " - Potencia: $potencia watts"
    }
}