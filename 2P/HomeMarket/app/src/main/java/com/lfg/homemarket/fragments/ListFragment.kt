package com.lfg.homemarket.fragments

import android.os.Bundle
import android.os.Handler
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
import com.lfg.homemarket.viewmodels.ListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

class ListFragment : Fragment(){

    companion object {
        fun newInstance() = ListFragment()
        private val SCAN_MSG_TIME_OUT:Long = 3000 // 3 sec
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
        viewModel.retrofit = ItemRetrofit (PreciosClarosServer.BASE_URL, requireContext()) { call -> onDataServerResponse(call) }
        viewModel.loadScannedId(requireContext())

        setupRecycler()

        val scope = CoroutineScope(Dispatchers.Main + Job())
        scope.launch {
            viewModel.getProductListFromCloud()
            adapterP.notifyDataSetChanged()
            binding.progressBarListView.visibility = ProgressBar.INVISIBLE
        }

        //viewModel.snapshotListener {
        //    adapterP.notifyDataSetChanged()
        //}

    }

    private fun showMessage(str : String, long : Boolean = false) {
        Toast.makeText(requireContext(), str, if(long)Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }

    private fun setupRecycler(){
        adapterP = ProductRecyclerAdapter(viewModel.getItemData()) {
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
                        viewModel.productList.removeAt(position)
                        adapterP.notifyDataSetChanged()
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

    private fun Fragment?.runOnUiThread(action: () -> Unit) {
        this ?: return
        if (!isAdded) return // Fragment not attached to an Activity
        activity?.runOnUiThread(action)
    }

    private fun onDataServerResponse( call : Response<ItemResponse>?) {
        runOnUiThread {
            if(call == null) {
                showMessage(ItemRetrofit.lastErrorMessage, true)
            }
            else {
                val pr = call.body()
                if (call.isSuccessful) {
                    if (pr?.status == 200) {
                        if (pr.producto.msg != "Producto inexistente.") {
                            try {
                                var precio = 0.0
                                val id = pr.producto.id.toLong()
                                Log.d("Producto", "Id: ${pr.producto.id} ${pr.producto.nombre}")
                                pr.sucursales.forEach { suc ->
                                    Log.d("Sucursal", "   ${suc.comercioRazonSocial} ${suc.preciosProducto.precioLista}")
                                    val price = if (suc.sucursalTipo == "Mayorista")
                                        suc.preciosProducto.precio_unitario_con_iva.toDouble()?:0.0
                                    else
                                        suc.preciosProducto.precioLista.toDouble()?:0.0
                                    if((price > 0 ) && (precio == 0.0))
                                        precio = price
                                }
                                val imageUrl = PreciosClarosServer.getProductImageUrl(pr.producto.id)
                                val prod = Product(
                                    id,
                                    pr.producto.marca,
                                    pr.producto.nombre,
                                    precio,
                                    pr.producto.presentacion,
                                    true,
                                    imageUrl)
                                viewModel.productList.add(prod)
                                viewModel.saveProductToDB(pr.producto.id, pr, prod)
                                adapterP.notifyDataSetChanged()
                                showMessage("Producto agregado: " + pr.producto.nombre,true)

                                var lastIndex = 0
                                for(index in 0 until viewModel.productList.size){
                                    if(viewModel.productList[index].id == id) {
                                        lastIndex = index
                                        break
                                    }
                                }

                                Handler().postDelayed(
                                    {
                                        //Accedo al último elemento agregado
                                        viewModel.saveDetailData(requireContext(),lastIndex)
                                        //Navegar
                                        val action = ListFragmentDirections.actionListFragmentToDetailFragment()
                                        binding.viewListFragment.findNavController().navigate(action)

                                    }
                                    , SCAN_MSG_TIME_OUT)

                            }
                            catch (ex: Exception) {
                                showMessage("ERROR al agregar el producto: " + pr.producto.nombre)
                            }
                        }
                        else
                            showMessage("Producto Inexistente")
                    }
                    else
                        showMessage("Error: Status ${pr?.status}")
                }else{
                    showMessage("Error: la llamada no fue exitosa")
                }
            }
        }
    }

}