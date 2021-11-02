package com.lfg.firestone.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.lfg.firestone.databinding.ListFragmentBinding
import com.lfg.firestone.entities.Mascota
import com.lfg.firestone.viewmodels.ListViewModel
import androidx.recyclerview.widget.RecyclerView as RecyclerView1

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private val viewModel: ListViewModel by viewModels()
    private lateinit var binding : ListFragmentBinding

    var mascotaList : MutableList<Mascota> = arrayListOf()
    lateinit var btnAdd : FloatingActionButton
    lateinit var recMascotas : RecyclerView1

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(layoutInflater)

        btnAdd = binding.btnAdd

        recMascotas = binding.recMascotas
        recMascotas.setHasFixedSize(true)
        recMascotas.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        var mascota : Mascota = Mascota("Pedro",Mascota.Constants.typePerro,"Colie",2,"imagen.com")

        db.collection("mascotas").document(mascota.nombre).set(mascota)

        db.collection("mascotas").add(mascota)


        viewModel.initTestList()


        for (mascota in viewModel.mascotas) {
            db.collection("mascotas").document(mascota.nombre).set(mascota)
        }

        // Leer datos una sola vez
        var docRef = db.collection("mascotas").document("Pedro")

        docRef.get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot != null) {
                    val mascota  = dataSnapshot.toObject<Mascota>()
                    Log.d("Test", "DocumentSnapshot data: ${mascota.toString()}")
                } else {
                    Log.d("Test", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Test", "get failed with ", exception)
            }

        // Se queda escuchando cambios
        docRef = db.collection("mascotas").document("Pedro")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.d("Test", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val mascota  = snapshot.toObject<Mascota>()
                Log.d("Test", "DocumentSnapshot data: ${mascota.toString()}")
            } else {
                Log.d("Test", "Current data: null")
            }
        }

        //traer lista de datos

        db.collection("mascotas")
//             .whereEqualTo("tipo", "PERRO")
//             .limit(20)
//             .orderBy("edad")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (mascota in snapshot) {
                        mascotaList.add(mascota.toObject())
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
}