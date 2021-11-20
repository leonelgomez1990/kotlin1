package com.lfg.homemarket.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lfg.homemarket.adapters.ProductRecyclerAdapter
import com.lfg.homemarket.clases.ItemResponse
import com.lfg.homemarket.clases.ItemRetrofit
import com.lfg.homemarket.clases.Product
import com.lfg.homemarket.databinding.ListFragmentBinding
import com.lfg.homemarket.functions.hideKeyboard
import com.lfg.homemarket.viewmodels.ListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

class ListFragment : Fragment(){

    companion object {
        fun newInstance() = ListFragment()
        private const val BASE_URL = "https://d3e6htiiul5ek9.cloudfront.net/prod/"
    }

    private val viewModel : ListViewModel by viewModels()
    private lateinit var binding : ListFragmentBinding
    private lateinit var adapterP: ProductRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getStoredCoordinates(requireContext())
        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Main + parentJob)
        setupRecycler()

        scope.launch {
            viewModel.getProductListFromCloud()
            adapterP.notifyDataSetChanged()
        }
        viewModel.retrofit = ItemRetrofit (BASE_URL) { call -> onProductResponse(call) }
        viewModel.loadScannedId(requireContext())
    }

    private fun showMessage(str : String) {
        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
    }

    private fun setupRecycler(){
        adapterP = ProductRecyclerAdapter(viewModel.getProductData()) {
                pos -> onItemClick(pos)
        }
        with(binding.recViewItems, {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterP
        })
    }

    private fun onProductResponse( call : Response<ItemResponse>) {
        val pr = call.body()
        requireActivity().runOnUiThread {
            if(call.isSuccessful){
                if(pr?.status == 200) {
                    if (pr.producto.msg != "Producto inexistente.") {
                        var precio = 0.0
                        Log.d("Producto", "Id: ${pr.producto.id} ${pr.producto.nombre}")
                        pr.sucursales.forEach { suc ->
                            Log.d("Sucursal", "   ${suc.comercioRazonSocial} ${suc.preciosProducto.precioLista}")
                            if(suc.preciosProducto.precioLista != "")
                                precio = suc.preciosProducto.precioLista.toDouble()

                        }
                        val imageUrl = "https://imagenes.preciosclaros.gob.ar/productos/${pr.producto.id}.jpg"
                        viewModel.productList.add(Product(pr.producto.id.toLong(), pr.producto.marca, pr.producto.nombre, precio, pr.producto.presentacion, imageUrl ))
                        val pr2 = Product(pr.producto.id.toLong(), pr.producto.marca, pr.producto.nombre, precio, pr.producto.presentacion, imageUrl )
                        viewModel.saveProductToDB(pr.producto.id, pr, pr2)
                        adapterP.notifyDataSetChanged()
                        showMessage("Producto agregado: " + pr.producto.nombre)
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



    private fun onItemClick (position : Int) {
        viewModel.saveDetailData(requireContext(),position, "view")
        //Navegar
        val action = ListFragmentDirections.actionListFragmentToDetailFragment()
        binding.viewListFragment.findNavController().navigate(action)
    }

}