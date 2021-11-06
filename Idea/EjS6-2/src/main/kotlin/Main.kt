import clases.Lavadora
import clases.Microondas
import clases.Refrigerador
import enum.ColorElectrodomestico

fun main () {
    //Imprimo título
    println("## Fabricación de Electrodomésticos ##\n")

    //Carga de datos
    val lav = Lavadora("SD5S84DRTR", ColorElectrodomestico.BLANCO, 25.0, 9500.99, 18.0)
    val micr = Microondas("AOS87DPSOR", ColorElectrodomestico.NEGRO, 8.5, 2100.0, 1350.0)
    val refrig = Refrigerador("SPDO8756SR", ColorElectrodomestico.GRIS, 47.0, 7899.0, 10.0, 0.0, -15.0)

    //Lista de electrodomésticos
    val listaElec = arrayOf(lav, micr, refrig)

    //Iteración
    listaElec.forEach { item ->
        println(item)
    }
    //Imprimo cantidad total
    println()
    println("Total de productos fabricados: ${listaElec.size}\n")
}