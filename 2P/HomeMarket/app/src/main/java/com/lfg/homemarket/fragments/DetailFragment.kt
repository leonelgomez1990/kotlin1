package com.lfg.homemarket.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lfg.homemarket.databinding.DetailFragmentBinding
import com.lfg.homemarket.viewmodels.DetailViewModel
import java.text.DecimalFormat

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding : DetailFragmentBinding

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

        viewModel.product.observe(viewLifecycleOwner, { result ->
            if(result.id > 0) {
                val strPrice = "$ ${DecimalFormat("#.00").format(result.price)}"
                viewModel.setViewImage(requireContext(),binding.imgDetailProduct, result.id.toString())
                binding.txtDetailName.text = result.description
                binding.txtDetailBrand.text = result.brand
                binding.txtDetailPresentation.text = result.presentation
                binding.txtDetailPrice.text = strPrice
            }
        })
    }



}