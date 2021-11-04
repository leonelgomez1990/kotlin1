package com.lfg.retrofit.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.lfg.retrofit.adapters.ProductAdapter
import com.lfg.retrofit.adapters.ProductRecyclerAdapter
import com.lfg.retrofit.clases.ItemResponse
import com.lfg.retrofit.clases.ItemRetrofit
import com.lfg.retrofit.clases.Product
import com.lfg.retrofit.databinding.ListFragmentBinding
import com.lfg.retrofit.functions.hideKeyboard
import com.lfg.retrofit.viewmodels.ListViewModel
import retrofit2.Response
import java.util.*

class ListFragment : Fragment(), SearchView.OnQueryTextListener{

    companion object {
        fun newInstance() = ListFragment()
    }

    private val viewModel : ListViewModel by viewModels()
    private lateinit var binding : ListFragmentBinding
    lateinit var retrofit: ItemRetrofit
    private lateinit var adapter: ProductAdapter
    private lateinit var adapterP: ProductRecyclerAdapter
    private val dogImages = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(layoutInflater)
        //initRecyclerView()
        setupRecycler()
        binding.searchViewItems.setOnQueryTextListener(this)
        retrofit = ItemRetrofit ("https://d3e6htiiul5ek9.cloudfront.net/prod/") { call -> onProductResponse(call) }
        return binding.root
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            retrofit.searchByQuery("producto?id_producto=${query.lowercase(Locale.getDefault())}&lat=-34.713078&lng=-58.497269")
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
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
                    viewModel.productList.add(Product(pr.producto.id.toLong(), pr.producto.marca, pr.producto.nombre, precio, pr.producto.presentacion, imageUrl ))
                    adapterP.notifyDataSetChanged()
                }

            }
            //show recyclerview
                //val images = product?.producto ?: emptyList()
                //dogImages.clear()
                //dogImages.addAll(images)
            }else{
                //show error
                showError()
            }
            hideKeyboard()
        }
    }

    private fun showError() {
        Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView() {
        adapter = ProductAdapter(dogImages)
        binding.recViewItems.layoutManager = LinearLayoutManager(requireContext())
        binding.recViewItems.adapter = adapter
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
    }
}