package com.lfg.miacell.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lfg.miacell.adapters.ProductAdapter
import com.lfg.miacell.databinding.FragmentListBinding
import com.lfg.miacell.repositories.ProductRepository
import com.lfg.miacell.viewmodels.ListViewModel

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private val viewModel: ListViewModel by viewModels()
    private lateinit var binding : FragmentListBinding
    private val PREF_NAME = "mySelection"
    private lateinit var linearLayoutManager : LinearLayoutManager
    private var productRepository = ProductRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setupRecycler()
    }

    private fun setupRecycler(){
        binding.recProduct.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        binding.recProduct.layoutManager = linearLayoutManager
        binding.recProduct.adapter = ProductAdapter(productRepository.getList(),requireContext()) {
                pos -> onItemClick(pos)
        }
    }

    private fun onItemClick (position : Int) {
        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("position",position)
        editor.apply()

        //val action = ListFragmentDirections.actionListFragmentToContainerFragment()
        //v.findNavController().navigate(action)
    }
}