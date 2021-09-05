package com.lfg.navactivity.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.lfg.navactivity.R
import com.lfg.navactivity.viewmodels.Fragment1ViewModel

class Fragment1 : Fragment() {

    companion object {
        fun newInstance() = Fragment1()
    }

    private lateinit var viewModel: Fragment1ViewModel
    lateinit var v : View
    lateinit var btnNavigate : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment1_fragment, container, false)
        btnNavigate = v.findViewById(R.id.btnNavigate)
        return v
    }

    override fun onStart() {
        super.onStart()
        btnNavigate.setOnClickListener {
            var action = Fragment1Directions.actionFragment1ToMainActivity2()
            findNavController().navigate(action)
        }

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Fragment1ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}