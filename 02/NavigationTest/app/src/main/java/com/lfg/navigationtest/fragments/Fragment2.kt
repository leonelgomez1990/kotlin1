package com.lfg.navigationtest.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lfg.navigationtest.R
import com.lfg.navigationtest.viewmodels.Fragment2ViewModel

class Fragment2 : Fragment() {

    companion object {
        fun newInstance() = Fragment2()
    }

    private lateinit var viewModel: Fragment2ViewModel
    lateinit var v : View
    lateinit var txtView2 : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment2_fragment, container, false)
        txtView2 = v.findViewById(R.id.textView2)
        return v
    }

    override fun onStart() {
        super.onStart()
        var recibido = Fragment2Args.fromBundle(requireArguments()).argumento
        txtView2.text = txtView2.text.toString() + " " + recibido.toString()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Fragment2ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}