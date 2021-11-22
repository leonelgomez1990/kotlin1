package com.lfg.homemarket.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lfg.homemarket.R
import com.lfg.homemarket.adapters.ProductRecyclerAdapter
import com.lfg.homemarket.clases.ItemResponse
import com.lfg.homemarket.clases.ItemRetrofit
import com.lfg.homemarket.clases.PreciosClarosServer
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
        binding.progressBarListView.visibility = ProgressBar.VISIBLE
        setupRecycler()

        val scope = CoroutineScope(Dispatchers.Main + Job())
        scope.launch {
            viewModel.getProductListFromCloud()
            adapterP.notifyDataSetChanged()
            binding.progressBarListView.visibility = ProgressBar.INVISIBLE
        }
        viewModel.retrofit = ItemRetrofit (PreciosClarosServer.BASE_URL) { call -> onProductResponse(call) }
        viewModel.loadScannedId(requireContext())

        viewModel.snapshotListener {
            adapterP.notifyDataSetChanged()
        }

    }

    private fun showMessage(str : String) {
        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
    }

    private fun setupRecycler(){
        adapterP = ProductRecyclerAdapter(viewModel.getProductData()) {
                event, pos -> onItemEventClick(event, pos)
        }
        with(binding.recViewItems, {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterP
        })
    }

    private fun onItemEventClick (event : ProductRecyclerAdapter.Companion.EventEnum, position : Int): Boolean {
        when (event) {
            ProductRecyclerAdapter.Companion.EventEnum.CLICK -> {
                viewModel.saveDetailData(requireContext(),position)
                //Navegar
                val action = ListFragmentDirections.actionListFragmentToDetailFragment()
                binding.viewListFragment.findNavController().navigate(action)
            }
            ProductRecyclerAdapter.Companion.EventEnum.LONGCLICK -> {
                android.app.AlertDialog.Builder(context) //set icon
                    .setIcon(R.drawable.ic_baseline_warning_24) //set title
                    .setTitle(R.string.dialog_del_title) //set message
                    .setMessage(R.string.dialog_del_msg) //set positive button
                    .setPositiveButton(
                        R.string.dialog_del_yes
                    ) { _, _ -> //set what would happen when positive button is clicked
                        viewModel.deleteProductInDB(viewModel.productList[position].id.toString())
                        showMessage("Se borró el elemento: " + viewModel.productList[position].description)
                    } //set negative button
                    .setNegativeButton(
                        R.string.dialog_del_no
                    ) { _, _ -> //set what should happen when negative button is clicked
                        showMessage("Cancelado")
                    }
                    .show()
            }
        }
        return true
    }

    private fun onProductResponse( call : Response<ItemResponse>) {
        val pr = call.body()
        requireActivity().runOnUiThread {
            if (call.isSuccessful) {
                if (pr?.status == 200) {
                    if (pr.producto.msg != "Producto inexistente.") {
                        var precio = 0.0
                        Log.d("Producto", "Id: ${pr.producto.id} ${pr.producto.nombre}")
                        pr.sucursales.forEach { suc ->
                            Log.d("Sucursal", "   ${suc.comercioRazonSocial} ${suc.preciosProducto.precioLista}")
                            if((suc.preciosProducto.precioLista != "") && (precio == 0.0))
                                precio = suc.preciosProducto.precioLista.toDouble()

                        }
                        val imageUrl = "https://imagenes.preciosclaros.gob.ar/productos/${pr.producto.id}.jpg"
                        val prod = Product(
                            pr.producto.id.toLong(),
                            pr.producto.marca,
                            pr.producto.nombre,
                            precio,
                            pr.producto.presentacion,
                            true,
                            imageUrl)
                        viewModel.productList.add(prod)
                        viewModel.saveProductToDB(pr.producto.id, pr, prod)
                        adapterP.notifyDataSetChanged()
                        showMessage("Producto agregado: " + pr.producto.nombre)
                    }
                    else
                        showMessage("Producto Inexistente")
                }
                else
                    showMessage("Error: Status ${pr?.status}")
                //show recyclerview
                //val images = product?.producto ?: emptyList()
                //dogImages.clear()
                //dogImages.addAll(images)
            }else{
                showMessage("Error: la llamada no fue exitosa")
            }
            hideKeyboard()
        }
    }

}