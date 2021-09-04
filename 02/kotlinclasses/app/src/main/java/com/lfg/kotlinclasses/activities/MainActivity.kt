package com.lfg.kotlinclasses.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lfg.kotlinclasses.R
import com.lfg.kotlinclasses.entities.Mascota
import com.lfg.kotlinclasses.repositories.RepositoryMascotas

class MainActivity : AppCompatActivity() {

    lateinit var miMascota: Mascota
    var edadCalculada : Int = 0
    var mascotas : MutableList<Mascota> = mutableListOf()
    var repositorio = RepositoryMascotas()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        miMascota = Mascota("Snoopy", Mascota.Constants.typePerro, "caniche", 7)
        Log.d("TEST", miMascota.toString())
        Log.d("TEST", miMascota.nombre + " " + miMascota.edad)
        miMascota.edad = 8
        miMascota.nombre = "Snupy"
        Log.d("TEST", miMascota.nombre + " " + miMascota.edad)
        edadCalculada = miMascota.calcularEdad(7)
        Log.d("TEST", edadCalculada.toString())
        mascotas.add(miMascota)

        mascotas.add(Mascota("Rodolfo",Mascota.Constants.typePerro,"Fox Terrier",4))
        mascotas.add(Mascota("Emilio",Mascota.Constants.typePerro,"Gran Danes",5))
        mascotas.add(Mascota("Luis",Mascota.Constants.typeGato,"Siames",6))
        mascotas.add(Mascota("Carlos",Mascota.Constants.typeGato,"Pardo",7))
        mascotas.add(Mascota("David",Mascota.Constants.typeGato,"Arlequin",8))

        for (mascotaActual in mascotas){
            Log.d("TEST", mascotaActual.toString())
        }
        mascotas[0].nombre = "NombreNuevo"
        Log.d("TEST", mascotas[0].nombre)

        mascotas.forEach {
            Log.d("TEST", it.nombre + " " + it.edad + "\n")
        }
        mascotas.forEach { mascota ->
            Log.d("TEST", mascota.nombre + " " + mascota.edad + "\n")
        }

        for (mascotaActual in repositorio.listMascotasRepository){
            Log.d("TEST", mascotaActual.toString())
        }
    }
}