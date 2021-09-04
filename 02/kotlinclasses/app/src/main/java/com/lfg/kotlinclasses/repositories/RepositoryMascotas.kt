package com.lfg.kotlinclasses.repositories

import com.lfg.kotlinclasses.entities.Mascota

class RepositoryMascotas {
    var listMascotasRepository : MutableList<Mascota> = ArrayList<Mascota>()

    init {
        listMascotasRepository.add(Mascota("Rodolfo",Mascota.Constants.typePerro,"Fox Terrier",4))
        listMascotasRepository.add(Mascota("Emilio",Mascota.Constants.typePerro,"Gran Danes",5))
        listMascotasRepository.add(Mascota("Luis",Mascota.Constants.typeGato,"Siames",6))
        listMascotasRepository.add(Mascota("Carlos",Mascota.Constants.typeGato,"Pardo",7))
        listMascotasRepository.add(Mascota("David",Mascota.Constants.typeGato,"Arlequin",8))
    }
}