package clases

/*
Clase para almacenar datos del cliente
 */
class Cuenta(private var clabe : String, private val titular : String, saldo : Double) {
    //Variables privadas de saldo actual y se graba también el saldo anterior a una operación
    private var saldoActual = 0.0
    private var saldoAnterior = 0.0
    /*
    Al crear la clase, se carga el saldo del cliente
     */
    init {
        saldoActual = saldo
    }

    /*
    Funcion depositar dinero
    @monto es el valor a aumentar el saldo del cliente
     */
    fun depositarDinero(monto : Double){
        saldoAnterior = saldoActual
        saldoActual += monto
    }

    /*
    Funcion retirar dinero
    @monto es el valor a disminuir el saldo del cliente
     */
    fun retirarDinero(monto : Double){
        saldoAnterior = saldoActual
        saldoActual -= monto
    }

    /*
    Funciones para obtener datos privados del cliente
     */
    fun obtenerSaldoActual() : Double = saldoActual
    fun obtenerSaldoAnterior() : Double = saldoAnterior
    fun obtenerDatosCliente() : String = "Clabe: $clabe - Titular: $titular"
}