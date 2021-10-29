/*
1. Una empresa de transporte cobra una tarifa general de $2.25 por kilómetro de trayecto.
Aplica una un descuento del 45% al total del viaje a personas de la tercera edad (60 años o
más) siempre y cuando presenten una identificación que acredite su edad.
La empresa necesita un programa en el cual el vendedor ingrese los kilómetros del trayecto,
si se debe aplicar el descuento y que imprima el total que se debe cobrar, siempre
redondeado y en números enteros.
Hay que tener en cuenta que para agilizar el proceso, es obligatorio introducir los kilómetros
del trayecto, pero el descuento es opcional, por lo que se puede dejar vacío. Se debe aplicar
el descuento solo si el usuario ingresa un si o un 1 cuando se le pregunte si aplica el
descuento.
 */
import kotlin.math.roundToInt

const val tarifaPorKm = 2.25  //Tarifa general por kilómetro de trayecto
const val descuentoTerceraEdad = 45.0 //Descuento del total del viaje a personas de tercera edad (en porcentaje)

fun main1() {
    print("Kilómetros del trayecto: ")
    val cantidadKm = readLine()
    if (cantidadKm.isNullOrBlank())
        println("Se deben ingresar los kilómetros del trayecto\n")
    else {
        var totalACobrar = cantidadKm.toDouble() * tarifaPorKm
        print("Aplicar descuento: ")
        //¿Aplica descuento?
        when (readLine()){
            "1", "si" -> totalACobrar *= (1 - descuentoTerceraEdad / 100)
        }
        println("Total a cobrar: $ ${totalACobrar.roundToInt()} \n")
    }
    //repite proceso
    main()
}
