package com.lfg.recyclerview.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lfg.recyclerview.R
import com.lfg.recyclerview.viewmodels.Fragment2ViewModel

class Fragment2 : Fragment() {

    companion object {
        fun newInstance() = Fragment2()
    }

    private lateinit var viewModel: Fragment2ViewModel
    lateinit var v : View
    lateinit var txtHello : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment2_fragment, container, false)
        txtHello = v.findViewById(R.id.txtHello)

        return v
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Fragment2ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}