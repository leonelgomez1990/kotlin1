import clases.Cuenta
import java.text.DecimalFormat

lateinit var cliente : Cuenta

fun main() {
    //Se cargan datos del cliente al comiezo del programa
    cliente = Cuenta("01-8547-9", "Alberto Palma", 5000.00)

    //Programa repetitivo
    while (true) {
        //Imprime menú
        println("Menú ")
        println("1 - Depositar")
        println("2 - Retirar\n")
        print("Ingresa la acción a realizar: ")

        //Se consideran sólo las respuestas 1 y 2, para el resto se sale del programa
        val accionIngresada = when (readLine()) {
            "1" -> Operacion.DEPOSITAR
            "2" -> Operacion.RETIRAR
            else -> {
                println("Saliendo del programa")
                return
            }
        }

        //Siguiente paso es ingresar el monto
        var montoCliente = 0.0
        print("Ingresa el monto: ")

        //Chequeo de monto válido
        try {
            montoCliente = readLine()!!.toDouble()
        }
        catch (ex : Exception) {
            println("Monto incorrecto")
        }
        if (montoCliente > 0) {
            if(accionIngresada == Operacion.DEPOSITAR) {
                cliente.depositarDinero(montoCliente)
            }
            else {
                //RETIRAR
                if(montoCliente > cliente.obtenerSaldoActual()) {
                    println("""
                El monto a retirar ($${printNumber(montoCliente)}) no puede ser mayor que el 
                saldo ($${printNumber(cliente.obtenerSaldoActual())}) 
                No se pudo realizar la acción solicitada
            """.trimIndent())
                    continue
                }
                else {
                    cliente.retirarDinero(montoCliente)
                }
            }
            //Ticket
            imprimirRecibo(accionIngresada, montoCliente)
        }
    }
}

fun imprimirRecibo (accionDeposito : Operacion, monto : Double) {
    println("""
            -------------- Recibo --------------
            
            ${cliente.obtenerDatosCliente()}
            
            Acción: $accionDeposito
            Monto: $${printNumber(monto)}
            Saldo anterior: $${printNumber(cliente.obtenerSaldoAnterior())}
            Nuevo Saldo: $${printNumber(cliente.obtenerSaldoActual())}
            
             -----------------------------------
        """.trimIndent())
}

enum class Operacion {
    DEPOSITAR {
        override fun toString() : String {
            return "Depósito de dinero"
        }
    },
    RETIRAR {
        override fun toString(): String {
            return "Retiro de dinero"
        }
    }
}

/*
Funcion para imprimir números en formato decimal dos dígitos
 */
fun printNumber (num : Double) : String {
    return DecimalFormat("#.00").format(num)
}