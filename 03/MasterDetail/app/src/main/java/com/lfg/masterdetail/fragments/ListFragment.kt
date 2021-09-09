package com.lfg.masterdetail.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lfg.masterdetail.R
import com.lfg.masterdetail.adapters.ProductAdapter
import com.lfg.masterdetail.repositories.ProductRepository
import com.lfg.masterdetail.viewmodels.ListViewModel

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModelList: ListViewModel
    private lateinit var v : View
    private lateinit var txtHello : TextView
    private lateinit var recProduct : RecyclerView
    private var productRepository = ProductRepository()
    private lateinit var linearLayoutManager : LinearLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.list_fragment, container, false)
        //Binding
        txtHello = v.findViewById(R.id.txtHello)
        recProduct = v.findViewById(R.id.recProduct)

        return v
    }

    override fun onStart() {
        super.onStart()
        setupRecycler()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelList = ViewModelProvider(this).get(ListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setupRecycler(){
        recProduct.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recProduct.layoutManager = linearLayoutManager
        recProduct.adapter = ProductAdapter(productRepository.getList(),requireContext()) {
            pos -> onItemClick(pos)
        }
    }

    private fun onItemClick (position : Int) {
        val action = ListFragmentDirections.actionFragmentListToDetailFragment(position)
        v.findNavController().navigate(action)
    }
}