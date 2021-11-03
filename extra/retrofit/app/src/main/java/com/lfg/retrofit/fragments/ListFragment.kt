package com.lfg.retrofit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.lfg.retrofit.clases.DogsResponse
import com.lfg.retrofit.clases.Retrofit
import com.lfg.retrofit.databinding.ListFragmentBinding
import com.lfg.retrofit.viewmodels.ListViewModel
import java.util.*

class ListFragment : Fragment(), SearchView.OnQueryTextListener{

    companion object {
        fun newInstance() = ListFragment()
    }

    private val viewModel : ListViewModel by viewModels()
    private lateinit var binding : ListFragmentBinding
    lateinit var retrofit: Retrofit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.svDogs.setOnQueryTextListener(this)
        Retrofit { dog ->
            dog?.let { onDogsResponse(it) }
        }.also { retrofit = it }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            retrofit.searchByName(query.lowercase(Locale.getDefault()))
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun onDogsResponse( puppy : DogsResponse) {

    }

}