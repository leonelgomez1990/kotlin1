package com.lfg.homemarket.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.lfg.homemarket.adapters.ProductRecyclerAdapter
import com.lfg.homemarket.clases.ItemResponse
import com.lfg.homemarket.clases.ItemRetrofit
import com.lfg.homemarket.clases.Product
import com.lfg.homemarket.databinding.ListFragmentBinding
import com.lfg.homemarket.functions.hideKeyboard
import com.lfg.homemarket.viewmodels.ListViewModel
import retrofit2.Response
import java.util.*

class ListFragment : Fragment(){

    companion object {
        fun newInstance() = ListFragment()
    }

    private val viewModel : ListViewModel by viewModels()
    private lateinit var binding : ListFragmentBinding
    lateinit var retrofit: ItemRetrofit
    private lateinit var adapterP: ProductRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(layoutInflater)

        retrofit = ItemRetrofit ("https://d3e6htiiul5ek9.cloudfront.net/prod/") { call -> onProductResponse(call) }

        try {
            val idRecibido  = ListFragmentArgs.fromBundle(requireArguments()).id
            if (idRecibido != "")
                saveToDbId(idRecibido!!)
        }
        catch (ex : Exception) {}

        return binding.root
    }

    fun saveToDbId(id : String) {
        retrofit.searchByQuery("producto?id_producto=${id.lowercase(Locale.getDefault())}&lat=-34.713078&lng=-58.497269")
    }

    override fun onStart() {
        super.onStart()

        setupRecycler()

        viewModel.productList.clear()
        //traer lista de datos
        viewModel.db.collection("listaproductos")
//             .whereEqualTo("tipo", "PERRO")
//             .limit(20)
//             .orderBy("edad")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (prod in snapshot) {
                        try {
                            val pr  = prod.toObject<Product>()
                            viewModel.productList.add(pr)
                        }
                        catch (ex: Exception) {
                            Log.w("DB", "Error getting documents: ", ex)
                        }
                    }
                    adapterP.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("DB", "Error getting documents: ", exception)
            }
    }

    private fun onProductResponse( call : Response<ItemResponse>) {
        val pr = call.body()
        requireActivity().runOnUiThread {
            if(call.isSuccessful){
                if(pr?.status == 200) {
                    if (pr.producto.msg != "Producto inexistente.") {
                        var precio = 0.0
                        Log.d("Producto", "Id: ${pr.producto.id.toString()} ${pr.producto.nombre}")
                        pr.sucursales.forEach { suc ->
                            Log.d("Sucursal", "   ${suc.comercioRazonSocial} ${suc.preciosProducto.precioLista}")
                            if(suc.preciosProducto.precioLista != "")
                                precio = suc.preciosProducto.precioLista.toDouble()

                        }
                        val imageUrl = "https://imagenes.preciosclaros.gob.ar/productos/${pr.producto.id}.jpg"
                        val pr2 = Product(pr.producto.id.toLong(), pr.producto.marca, pr.producto.nombre, precio, pr.producto.presentacion, imageUrl )
                        viewModel.saveProductToDB(pr.producto.id, pr, pr2)
                        adapterP.notifyDataSetChanged()
                    }
                    else
                        showMessage("Producto Inexistente")
                }
                else
                    showMessage("Status distinto de 200")
                //show recyclerview
                //val images = product?.producto ?: emptyList()
                //dogImages.clear()
                //dogImages.addAll(images)
            }else{
                //show error
                showMessage("Ha ocurrido un error")
            }
            hideKeyboard()
        }
    }

    private fun showMessage(str : String) {
        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
    }

    private fun setupRecycler(){
        adapterP = ProductRecyclerAdapter(viewModel.getProductData(),requireContext()) {
                pos -> onItemClick(pos)
        }
        binding.recViewItems.setHasFixedSize(true)
        binding.recViewItems.layoutManager = LinearLayoutManager(requireContext())
        binding.recViewItems.adapter = adapterP
    }

    private fun onItemClick (position : Int) {
        viewModel.saveDetailData(requireContext(),position, "view")
        //Navegar
        val action = ListFragmentDirections.actionListFragmentToDetailFragment()
        binding.viewListFragment.findNavController().navigate(action)


    }

}