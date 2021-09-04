package com.lfg.gaby.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lfg.gaby.R
import com.lfg.gaby.viewmodels.Fragment2ViewModel
import kotlinx.android.synthetic.main.fragment2_fragment.*

class Fragment2 : Fragment() {

    companion object {
        fun newInstance() = Fragment2()
    }

    private lateinit var viewModel: Fragment2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment2_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        var modeloCelu  = Fragment2Args.fromBundle(requireArguments()).modelo
        txtModelo.text = modeloCelu
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Fragment2ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}