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
import com.lfg.retrofit.clases.ItemResponse
import com.lfg.retrofit.clases.ItemRetrofit
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
    private val dogImages = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(layoutInflater)
        initRecyclerView()
        binding.svDogs.setOnQueryTextListener(this)
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
                    Log.d("Producto", "Id: ${pr.producto.id.toString()} ${pr.producto.nombre}")
                    pr.sucursales.forEach { suc ->
                        Log.d("Sucursal", "   ${suc.comercioRazonSocial} ${suc.preciosProducto.precioLista}")

                    }
                }

            }
            //show recyclerview
                //val images = product?.producto ?: emptyList()
                //dogImages.clear()
                //dogImages.addAll(images)
                //adapter.notifyDataSetChanged()
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
        binding.rvDogs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDogs.adapter = adapter
    }
}