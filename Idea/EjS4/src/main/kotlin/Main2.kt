/*
Declarar dos arreglos con los datos de la siguiente tabla en un programa
 */

val listaAlumnos = arrayOf("José", "Alberto", "Laura", "Noel", "Erika", "Daniel")
val listaCalificaciones = arrayOf(31.0, 94.0, 98.5, 75.0, 46.5, 75.0)

fun main2() {

    println("""
        Promedio de calificación: ${calculoPromedioCalificacion()} 
        
        Calificación más alta: ${encontrarCalificacionAlta()}
        Calificación más baja: ${encontrarCalificacionBaja()}
        
        Reprobados: 
    """.trimIndent())
    println(listadoReprobados())
}

fun calculoPromedioCalificacion() : Double {
    var sumatoria = 0.0
    listaCalificaciones.forEach { nota ->
        sumatoria += nota
    }
    return sumatoria / listaCalificaciones.size
}

fun encontrarCalificacionAlta() : Double {
    var notaAlta = 0.0
    for (i in 0 until listaCalificaciones.size) {
        val nota = listaCalificaciones[i]
        // Si es el primero actualizamos el valor
        if(i == 0)
            notaAlta = nota
        if(notaAlta < nota) {
            //Cuando se encuentre una nota más alta, se actualiza
            notaAlta = nota
        }
    }
    return notaAlta
}

fun encontrarCalificacionBaja() : Double {
    var notaBaja = 0.0
    for (i in 0 until listaCalificaciones.size) {
        val nota = listaCalificaciones[i]
        // Si es el primero actualizamos el valor
        if(i == 0)
            notaBaja = nota
        if(notaBaja > nota) {
            //Cuando se encuentre una nota más baja, se actualiza
            notaBaja = nota
        }
    }
    return notaBaja
}

fun listadoReprobados() : String {
    var msg = ""
    for (i in 0 until listaCalificaciones.size) {
        val nota = listaCalificaciones[i]
        val alumno = listaAlumnos[i]
        // Si esta reprobado lo agregamos al listado
        if(nota < 60.0){
            if (msg != "") msg += "\n"
            msg += "${alumno}, ${nota}"
        }
    }
    return msg
}