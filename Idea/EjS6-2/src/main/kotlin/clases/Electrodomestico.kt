package clases

import enum.ColorElectrodomestico
import enum.TipoElectrodomestico

/*
Clase de Electrodoméstico
parámetros: numeroSerie, tipo, color, peso y precio
 */
abstract class Electrodomestico (
    var numeroSerie : String,
    var tipo : TipoElectrodomestico,
    var color : ColorElectrodomestico,
    var peso : Double,
    var precio : Double
        ) {

    //Cada vez que se llama se sube el contador
    init {
        totalElectrodomesticosFabricados++
    }

    companion object {
        //Propiedad estática de la clase
        var totalElectrodomesticosFabricados = 0
    }

    override fun toString(): String {
        return "Número de Serie: $numeroSerie - Tipo: $tipo - Color: $color - Peso: $peso kg - Precio: $$precio"
    }
}