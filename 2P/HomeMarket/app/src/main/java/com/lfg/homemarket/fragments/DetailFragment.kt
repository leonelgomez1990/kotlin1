package com.lfg.homemarket.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lfg.homemarket.adapters.BranchRecyclerAdapter
import com.lfg.homemarket.clases.*
import com.lfg.homemarket.databinding.DetailFragmentBinding
import com.lfg.homemarket.viewmodels.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.DecimalFormat

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding : DetailFragmentBinding
    private lateinit var adapterP: BranchRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStartDetail(requireContext())
        binding.progressBarDetailView.visibility = ProgressBar.VISIBLE
        viewModel.retrofit = ItemRetrofit (PreciosClarosServer.BASE_URL) { call -> onDataServerResponse(call) }
        setupRecycler()

        viewModel.product.observe(viewLifecycleOwner, { result ->
            if(result.id > 0) {
                val strPrice = "$ ${DecimalFormat("#.00").format(result.price)}"
                viewModel.setViewImage(requireContext(),binding.imgDetailProduct, result.id.toString())
                binding.txtDetailName.text = result.description
                binding.txtDetailBrand.text = result.brand
                binding.txtDetailPresentation.text = result.presentation
                binding.txtDetailPrice.text = strPrice

                val scope = CoroutineScope(Dispatchers.Main + Job())
                scope.launch {
                    if(viewModel.getBranchListFromCloud()) {
                        adapterP.notifyDataSetChanged()
                        binding.progressBarDetailView.visibility = ProgressBar.INVISIBLE
                    }
                }
            }
        })
    }

    private fun setupRecycler(){
        adapterP = BranchRecyclerAdapter(viewModel.getItemData()) {
                event, pos -> onItemEventClick(event, pos)
        }
        with(binding.recDetailMarket, {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterP
        })
    }

    private fun onItemEventClick (event : BranchRecyclerAdapter.Companion.EventEnum, position : Int): Boolean {
        when (event) {
            BranchRecyclerAdapter.Companion.EventEnum.CLICK -> {
            }
            BranchRecyclerAdapter.Companion.EventEnum.LONGCLICK -> {
            }
        }
        return true
    }

    private fun onDataServerResponse( call : Response<ItemResponse>) {
        var errorText = ""
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
                                val price = if (suc.sucursalTipo == "Mayorista")
                                    suc.preciosProducto.precio_unitario_con_iva.toDouble()
                                else
                                    suc.preciosProducto.precioLista.toDouble()
                                val urlImage = "https://imagenes.preciosclaros.gob.ar/comercios/${suc.comercioId}-${suc.banderaId}.jpg"

                                val branch = PriceBranch(
                                    pr.producto.id.toLong(),
                                    suc.sucursalTipo,
                                    suc.banderaDescripcion,
                                    price,
                                    suc.direccion,
                                    suc.distanciaNumero,
                                    urlImage
                                )
                                viewModel.branchList.add(branch)
                        }
                        viewModel.saveTodayDataToDB(pr.producto.id, pr)
                        errorText = "Producto agregado: " + pr.producto.nombre
                        adapterP.notifyDataSetChanged()
                        binding.progressBarDetailView.visibility = ProgressBar.INVISIBLE
                    }
                    else
                        errorText = "Producto Inexistente"
                }
                else
                    errorText = "Error: Status ${pr?.status}"
            }else{
                errorText = "Error: la llamada no fue exitosa"
            }
        }
    }
}