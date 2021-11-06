import kotlin.math.roundToInt

//Tabla de cantidad de ingredientes para cada dona
//(harina, levadura, azucar, leche, mantequilla, huevo, sal, aceite)
val cantidadIngrediente = arrayOf(
    20.0,
    1.0,
    7.5,
    3.75,
    1.5,
    0.15,
    0.05,
    12.0
)
//Es la mínima cantidad de donas para realizar un pedido
const val CANTIDAD_MINIMA_DONAS = 20

var nombrePrograma: String? = null

fun main() {

    //Se imprime el nombre del programa
    nombrePrograma = "\n## Ingredientes y costo de pedidos de donas ##"
    println(nombrePrograma!!)

    //Pedido de datos al usuario y chequeo de valor
    print("\nTotal de donas a elaborar: ")
    var totalDonasAElaborar : Int = 0
    try {
        totalDonasAElaborar = readLine()!!.toInt()
    }
    catch (ex : Exception) {
        println("Se debe ingresar un número entero, intenta nuevamente")
        return main()
    }

    //Verificación de pedido mínimo
    if(totalDonasAElaborar < CANTIDAD_MINIMA_DONAS)
    {
        println("El pedido debe ser de mínimo $CANTIDAD_MINIMA_DONAS donas")
        return main()
    }

    //Cálculo del precio total a cobrar
    val totalACobrar = if (totalDonasAElaborar < 100) {
        totalDonasAElaborar * 6.0
    } else {
        totalDonasAElaborar * 5.55
    }

    //Se imprime resultado
    println(
        """
        ***********************************
        Ingredientes para $totalDonasAElaborar donas

        Harina       | ${(cantidadIngrediente[0] * totalDonasAElaborar).roundToInt()} gramos
        Levadura     | ${(cantidadIngrediente[1] * totalDonasAElaborar).roundToInt()} gramos
        Azucar       | ${(cantidadIngrediente[2] * totalDonasAElaborar).roundToInt()} gramos
        Leche        | ${(cantidadIngrediente[3] * totalDonasAElaborar).roundToInt()} mililitros
        Mantequilla  | ${(cantidadIngrediente[4] * totalDonasAElaborar).roundToInt()} gramos
        Huevo        | ${(cantidadIngrediente[5] * totalDonasAElaborar).roundToInt()} huevos
        Sal          | ${(cantidadIngrediente[6] * totalDonasAElaborar).roundToInt()} gramos
        Aceite       | ${(cantidadIngrediente[7] * totalDonasAElaborar).roundToInt()} mililitros
        ***********************************
        Total a cobrar: $${totalACobrar.roundToInt()}
        ***********************************
    """
    )

    //Nuevo inicio de programa
    main()

}