package com.lfg.miacell.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.lfg.miacell.databinding.FragmentDetailBinding
import com.lfg.miacell.viewmodels.DetailViewModel

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding : FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        viewModel.onCreateDB(binding.layoutDetail.context)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStartDetail(requireContext())

        viewModel.setViewImage(requireContext(), binding.imgDetail, "")

        viewModel.product.observe(viewLifecycleOwner, { result ->
            binding.txtDetailId.setText(result.id.toString())
            binding.txtDetailBrand.setText(result.brand.toString())
            binding.txtDetailDescription.setText(result.description.toString())
            binding.txtDetailPrice.setText("$ " + result.price.toString())
        })

        binding.btnDetailEdit.setOnClickListener {
            viewModel.setMode("edit")
        }

        binding.imgDetail.setOnClickListener {
            viewModel.setViewImage(requireContext(),binding.imgDetail, binding.txtDetailId.text.toString())
        }

        viewModel.mode.observe(viewLifecycleOwner, { result ->
            when (result.toString()) {
                "view" -> {
                    binding.txtDetailId.isEnabled = false
                    binding.txtDetailBrand.isEnabled = false
                    binding.txtDetailDescription.isEnabled = false
                    binding.txtDetailPrice.isEnabled = false
                    binding.btnDetailCancel.isVisible = false
                    binding.btnDetailSave.isVisible = false
                }
                "add" -> {
                    binding.txtDetailId.isEnabled = true
                    binding.txtDetailBrand.isEnabled = true
                    binding.txtDetailDescription.isEnabled = true
                    binding.txtDetailPrice.isEnabled = true
                    binding.btnDetailCancel.isVisible = true
                    binding.btnDetailSave.isVisible = true
                    binding.btnDetailEdit.isVisible = false
                }
                "edit" -> {
                    binding.txtDetailId.isEnabled = true
                    binding.txtDetailBrand.isEnabled = true
                    binding.txtDetailDescription.isEnabled = true
                    binding.txtDetailPrice.isEnabled = true
                    binding.btnDetailCancel.isVisible = true
                    binding.btnDetailSave.isVisible = true
                    binding.btnDetailEdit.isVisible = false
                }
                else -> {
                }
            }
        })

    }

}