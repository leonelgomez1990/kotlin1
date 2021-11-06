package clases

import enum.ColorElectrodomestico
import enum.TipoElectrodomestico

/*
Clase de Refrigerador, hereda de Electrodomestico
parámetros: numeroSerie, color, peso y precio, capacidad, tempMinConservador, tempMinCongelador
 */
class Refrigerador (
        numeroSerie : String,
        color : ColorElectrodomestico,
        peso : Double,
        precio : Double,
        var capacidad : Double,
        var tempMinConservador : Double,
        var tempMinCongelador : Double
) : Electrodomestico(numeroSerie, TipoElectrodomestico.REFRIGERADOR, color, peso, precio) {

        override fun toString(): String {
                return super.toString() + " - Capacidad: $capacidad ft³ - Temperatura mínima: [$tempMinConservador °C conservador][$tempMinCongelador °C congelador]"
        }
}