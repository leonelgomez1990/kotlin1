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
import androidx.recyclerview.widget.LinearLayoutManager
import com.lfg.homemarket.adapters.BranchRecyclerAdapter
import com.lfg.homemarket.clases.*
import com.lfg.homemarket.databinding.DetailFragmentBinding
import com.lfg.homemarket.viewmodels.DetailViewModel
import kotlinx.coroutines.*
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
        viewModel.retrofit = ItemRetrofit (PreciosClarosServer.BASE_URL, requireContext()) { call -> onDataServerResponse(call) }
        setupRecycler()

        viewModel.product.observe(viewLifecycleOwner, { result ->
            if(result.id > 0) {
                val strPrice = "$ ${DecimalFormat("0.00").format(result.price)}"
                viewModel.setViewImage(requireContext(),binding.imgDetailProduct, result.id.toString())
                binding.txtDetailName.text = result.description
                binding.txtDetailBrand.text = result.brand
                binding.txtDetailPresentation.text = result.presentation
                binding.txtDetailPrice.text = strPrice

                val scope = CoroutineScope(Dispatchers.Main + Job())
                scope.async {
                    val routineResult = viewModel.getBranchListFromCloud()
                    runOnUiThread {
                        when(routineResult) {
                            "OK" -> {
                                adapterP.notifyDataSetChanged()
                                binding.progressBarDetailView.visibility = ProgressBar.INVISIBLE
                            }
                            "RETROFIT" -> {
                            }
                            else -> {
                                showMessage(routineResult)
                                //showMessage(getString(R.string.internet_msg_not_connection))
                                binding.progressBarDetailView.visibility = ProgressBar.INVISIBLE
                            }
                        }
                    }
                }
            }
        })

        binding.imgDetailProduct.setOnClickListener {
            viewModel.setViewImage(requireContext(),binding.imgDetailProduct,viewModel.product.value?.id.toString())
        }
    }

    private fun setupRecycler(){
        adapterP = BranchRecyclerAdapter(viewModel.getItemData(), requireContext()) {
                event, pos -> onItemEventClick(event, pos)
        }
        with(binding.recDetailMarket, {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterP
        })
    }

    private fun showMessage(str : String, long : Boolean = false) {
        Toast.makeText(requireContext(), str, if(long)Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }

    private fun onItemEventClick (event : BranchRecyclerAdapter.Companion.EventEnum, position : Int): Boolean {
        when (event) {
            BranchRecyclerAdapter.Companion.EventEnum.CLICK -> {
            }
            BranchRecyclerAdapter.Companion.EventEnum.LONGCLICK -> {
            }
            BranchRecyclerAdapter.Companion.EventEnum.IMGCLICK -> {
                showMessage(viewModel.branchList[position].urlImage)
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
                var errorText = ""
                val pr = call.body()
                if (call.isSuccessful) {
                    if (pr?.status == 200) {
                        if (pr.producto.msg != "Producto inexistente.") {
                            var precio = 0.0
                            var lowPrice = 0.0
                            var price = 0.0
                            Log.d("Producto", "Id: ${pr.producto.id} ${pr.producto.nombre}")
                            pr.sucursales.forEach { suc ->
                                Log.d("Sucursal", "   ${suc.comercioRazonSocial} ${suc.preciosProducto.precioLista}")
                                if((suc.preciosProducto.precioLista != "") && (precio == 0.0))
                                    precio = suc.preciosProducto.precioLista.toDouble()
                                price = if (suc.sucursalTipo == "Mayorista")
                                    suc.preciosProducto.precio_unitario_con_iva.toDouble()
                                else
                                    suc.preciosProducto.precioLista.toDouble()
                                if((lowPrice == 0.0) || (lowPrice > price))
                                    lowPrice = price

                                val urlImage = PreciosClarosServer.getBranchImageUrl(suc.comercioId, suc.banderaId)

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
                            viewModel.checkNewProductValue(price)

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
}