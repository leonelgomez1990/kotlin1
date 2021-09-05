package com.lfg.navigationtest.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lfg.navigationtest.R
import com.lfg.navigationtest.viewmodels.Fragment1ViewModel

class Fragment1 : Fragment() {

    companion object {
        fun newInstance() = Fragment1()
    }

    private lateinit var viewModel: Fragment1ViewModel
    lateinit var v : View
    lateinit var btnNavegar : Button
    lateinit var txtUser : TextView
    lateinit var txtPassword : TextView
    lateinit var rootLayout : ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment1_fragment, container, false)
        btnNavegar = v.findViewById(R.id.btnNavigate)
        txtUser = v.findViewById(R.id.txtUser)
        txtPassword = v.findViewById(R.id.txtPassword)
        rootLayout = v.findViewById(R.id.frameLayoutFr1)
        return v
    }

    override fun onStart() {
        super.onStart()

        btnNavegar.setOnClickListener {
            if (txtUser.length() > 0) {
                var action = Fragment1Directions.actionFragment1ToFragment2(txtUser.text.toString())
                findNavController().navigate(action)
            }
            else {
                Snackbar.make(rootLayout,R.string.msg_Empty,Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Fragment1ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}