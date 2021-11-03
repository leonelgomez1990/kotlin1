package com.lfg.retrofit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.lfg.retrofit.adapters.DogAdapter
import com.lfg.retrofit.clases.DogsResponse
import com.lfg.retrofit.clases.Retrofit
import com.lfg.retrofit.databinding.ListFragmentBinding
import com.lfg.retrofit.functions.hideKeyboard
import com.lfg.retrofit.viewmodels.ListViewModel
import retrofit2.Response
import java.util.*

class ListFragment : Fragment(), SearchView.OnQueryTextListener{

    companion object {
        fun newInstance() = ListFragment()
    }

    private val viewModel : ListViewModel by viewModels()
    private lateinit var binding : ListFragmentBinding
    lateinit var retrofit: Retrofit
    private lateinit var adapter: DogAdapter
    private val dogImages = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(layoutInflater)
        initRecyclerView()
        binding.svDogs.setOnQueryTextListener(this)
        retrofit = Retrofit { call -> onDogsResponse(call) }
        return binding.root
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

    private fun onDogsResponse( call : Response<DogsResponse>) {
        val puppies = call.body()
        requireActivity().runOnUiThread {
            if(call.isSuccessful){
                //show recyclerview
                val images = puppies?.images ?: emptyList()
                dogImages.clear()
                dogImages.addAll(images)
                adapter.notifyDataSetChanged()
            }else{
                //show error
                showError()
            }
            hideKeyboard()
        }
    }

    private fun showError() {
        Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView() {
        adapter = DogAdapter(dogImages)
        binding.rvDogs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDogs.adapter = adapter
    }
}