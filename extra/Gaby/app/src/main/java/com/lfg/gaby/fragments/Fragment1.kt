package com.lfg.gaby.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.lfg.gaby.R
import com.lfg.gaby.viewmodels.Fragment1ViewModel

class Fragment1 : Fragment() {

    companion object {
        fun newInstance() = Fragment1()
    }

    private lateinit var viewModel: Fragment1ViewModel
    lateinit var v : View

    lateinit var btnMod1 : Button
    lateinit var btnMod2 : Button
    lateinit var btnMod3 : Button
    lateinit var btnMod4 : Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment1_fragment, container, false)

        btnMod1 = v.findViewById(R.id.btnMod1)
        btnMod2 = v.findViewById(R.id.btnMod2)
        btnMod3 = v.findViewById(R.id.btnMod3)
        btnMod4 = v.findViewById(R.id.btnMod4)

        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onStart() {
        super.onStart()

        btnMod1.setOnClickListener {
            var action = Fragment1Directions.actionFragment1ToFragment2(getString(R.string.mod_1))
            findNavController().navigate(action)
        }
        btnMod2.setOnClickListener {
            var action = Fragment1Directions.actionFragment1ToFragment2(getString(R.string.mod_2))
            findNavController().navigate(action)
        }
        btnMod3.setOnClickListener {
            var action = Fragment1Directions.actionFragment1ToFragment2(getString(R.string.mod_3))
            findNavController().navigate(action)
        }
        btnMod4.setOnClickListener {
            var action = Fragment1Directions.actionFragment1ToFragment2(getString(R.string.mod_4))
            findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Fragment1ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}