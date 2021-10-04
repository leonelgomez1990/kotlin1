package com.lfg.miacell.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lfg.miacell.adapters.ProductAdapter
import com.lfg.miacell.databinding.FragmentListBinding
import com.lfg.miacell.viewmodels.ListViewModel

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private val viewModel: ListViewModel by viewModels()
    private lateinit var binding : FragmentListBinding
    private lateinit var linearLayoutManager : LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater)
        viewModel.onCreateDB(binding.layoutList.context)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setupRecycler()

        binding.btnListAdd.setOnClickListener {
            viewModel.savePosition(requireContext(), -1)
            viewModel.saveDetailMode(requireContext(), "add")
            val action = ListFragmentDirections.actionListFragmentToDetailFragment()
            binding.layoutList.findNavController().navigate(action)
        }
    }

    private fun setupRecycler(){
        binding.recProduct.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        binding.recProduct.layoutManager = linearLayoutManager
        binding.recProduct.adapter = ProductAdapter(viewModel.getProductData(),requireContext()) {
                pos -> onItemClick(pos)
        }
    }

    private fun onItemClick (position : Int) {
        viewModel.savePosition(requireContext(), position)
        viewModel.saveDetailMode(requireContext(), "view")
        val action = ListFragmentDirections.actionListFragmentToDetailFragment()
        binding.layoutList.findNavController().navigate(action)
    }
}