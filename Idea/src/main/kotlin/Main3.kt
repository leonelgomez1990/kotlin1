/*
Crear un programa que lea una cantidad indefinida de números (uno por uno) hasta que
el usuario ingrese un 0, en ese momento imprimir la sumatoria de los números y el
promedio.
 */
fun main() {
    var listadoNumeros = arrayListOf<Double>()
    var sumatoria = 0.0
    var numeroIngresado : Double

    numeroIngresado = readLine()!!.toDouble()
    while (numeroIngresado != 0.0) {
        listadoNumeros.add(numeroIngresado)
        sumatoria += numeroIngresado
        numeroIngresado = readLine()!!.toDouble()
    }
    println("Sumatoria: $sumatoria")
    println("Promedio: ${sumatoria / listadoNumeros.size}\n")
    //repite proceso
    main()
}
