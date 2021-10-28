import java.text.DecimalFormat

fun main() {
    ejercicio3()
}

/*
1. Crear un programa que solicite al usuario los datos de su dirección, pidiendo el nombre
de la calle, la ciudad, el estado o provincia, el país y el código postal. Imprimir en consola la
dirección del usuario concatenada en una sola línea y separada por comas.
 */
fun ejercicio1() {
    print("Calle: ")
    val calle = readLine().toString()
    print("Ciudad: ")
    val ciudad = readLine()
    print("Estado: ")
    val provincia = readLine()
    print("País: ")
    val pais = readLine()
    print("Código postal: ")
    val codigoPostal = readLine()

    println("${calle}, ${ciudad}, ${provincia}, ${pais}, ${codigoPostal}")
}

/*
2. Crear un programa que solicite al usuario su salario mensual bruto y el porcentaje de
impuestos que debe pagar por mes, imprimir en consola cuál será su salario neto (al mes y
al año) y cuánto dinero debe pagar de impuestos (al mes y al año). Mostrar los datos de
salida con un máximo de dos posiciones decimales.
 */
fun ejercicio2() {
    print("Salario mensual bruto: ")
    val salarioMensualBruto = readLine()!!.toDouble()
    print("Impuesto mensual (porcentaje): ")
    val impuestoMensual = readLine()!!.toDouble()

    val impuestoPagoMensual = (salarioMensualBruto / 100 ) * impuestoMensual
    val salarioMensualNeto = salarioMensualBruto - impuestoPagoMensual

    val salarioAnualNeto = salarioMensualNeto * 12
    val impuestoPagoAnual = impuestoPagoMensual * 12
    val formato = "#.00"

    println("\nSalario mensual neto: $${DecimalFormat(formato).format(salarioMensualNeto)}")
    println("Impuestos a pagar por mes: $${DecimalFormat(formato).format(impuestoPagoMensual)}\n")
    println("Salario anual neto: $${DecimalFormat(formato).format(salarioAnualNeto)}")
    println("Impuestos a pagar por año: $${DecimalFormat(formato).format(impuestoPagoAnual)}")
}

/*
3. Crear un programa que calcule al área y el perímetro o circunferencia de un círculo, en base a lo siguiente:
 El usuario debe ingresar solo el radio del círculo en centímetros.
 El cálculo de los valores debe realizarse en funciones, una para el área y otra para la
circunferencia.
 Imprimir el resultado (área y circunferencia) en centímetros y en pulgadas con el
mismo formato que se muestra en el ejemplo y con un máximo de dos posiciones
decimales.
Ej datos de entrada:
10
17.25
 */
fun ejercicio3() {
    print("Ingresa el radio en centímetros: ")
    val radioCirculoCm = readLine()!!.toDouble()
    val formato = "#.00"
    val radioCirculoInch : Double = radioCirculoCm / 2.54
    println("""
        *** Centímetros ***
        Área               ${DecimalFormat(formato).format(calculoArea(radioCirculoCm))}
        Circunferencia     ${DecimalFormat(formato).format(calculoCircunferencia(radioCirculoCm))}
        
        *** Pulgadas ***
        Área               ${DecimalFormat(formato).format(calculoArea(radioCirculoInch))}
        Circunferencia     ${DecimalFormat(formato).format(calculoCircunferencia(radioCirculoInch))}
    """.trimIndent())
}

fun calculoArea(radio : Double) : Double {
    val pi = 3.14159
    return pi * radio * radio
}

fun calculoCircunferencia(radio : Double) : Double {
    val pi = 3.14159
    return pi * 2 * radio
}
