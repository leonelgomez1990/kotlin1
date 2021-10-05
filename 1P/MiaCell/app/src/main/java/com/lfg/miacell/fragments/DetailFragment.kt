package com.lfg.miacell.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lfg.miacell.databinding.FragmentDetailBinding
import com.lfg.miacell.functions.hideKeyboard
import com.lfg.miacell.viewmodels.DetailViewModel

class DetailFragment : Fragment() {

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
            binding.txtDetailBrand.setText(result.brand)
            binding.txtDetailDescription.setText(result.description)
            binding.txtDetailPrice.setText(result.price.toString())
            viewModel.setViewImage(requireContext(),binding.imgDetail, binding.txtDetailId.text.toString())
            binding.txtDetailId.requestFocus()
        })

        viewModel.back.observe(viewLifecycleOwner, { result ->
            if(result)
                findNavController().popBackStack()
        })

        binding.btnDetailEdit.setOnClickListener {
            viewModel.setMode("edit")
        }

        binding.imgDetail.setOnClickListener {
            viewModel.setViewImage(requireContext(),binding.imgDetail, binding.txtDetailId.text.toString())
        }

        binding.btnDetailSave.setOnClickListener {
            hideKeyboard()
            when (viewModel.mode.value.toString()) {
                "add" -> {
                    viewModel.setProductData(binding.txtDetailId.text.toString(),binding.txtDetailBrand.text.toString(),binding.txtDetailDescription.text.toString(),binding.txtDetailPrice.text.toString())
                    viewModel.addItem(binding.layoutDetail)
                    viewModel.setMode("view")
                }
                "edit" -> {
                    viewModel.setProductData(binding.txtDetailId.text.toString(),binding.txtDetailBrand.text.toString(),binding.txtDetailDescription.text.toString(),binding.txtDetailPrice.text.toString())
                    viewModel.editItem(binding.layoutDetail)
                    viewModel.setMode("view")
                }
                else -> {
                }
            }
        }

        binding.btnDetailCancel.setOnClickListener {
            if(viewModel.mode.value == "add")
                findNavController().popBackStack()
            else
                viewModel.setMode("view")
        }
        binding.btnDetailDelete.setOnClickListener {
           viewModel.deteleItem(binding.layoutDetail)
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
                    binding.btnDetailEdit.isVisible = true
                    binding.btnDetailDelete.isVisible = true
                }
                "add" -> {
                    binding.txtDetailId.isEnabled = true
                    binding.txtDetailBrand.isEnabled = true
                    binding.txtDetailDescription.isEnabled = true
                    binding.txtDetailPrice.isEnabled = true
                    binding.btnDetailCancel.isVisible = true
                    binding.btnDetailSave.isVisible = true
                    binding.btnDetailEdit.isVisible = false
                    binding.btnDetailDelete.isVisible = false
                }
                "edit" -> {
                    binding.txtDetailId.isEnabled = false
                    binding.txtDetailBrand.isEnabled = true
                    binding.txtDetailDescription.isEnabled = true
                    binding.txtDetailPrice.isEnabled = true
                    binding.btnDetailCancel.isVisible = true
                    binding.btnDetailSave.isVisible = true
                    binding.btnDetailEdit.isVisible = false
                    binding.btnDetailDelete.isVisible = true
                }
                else -> {
                }
            }
        })

    }

}