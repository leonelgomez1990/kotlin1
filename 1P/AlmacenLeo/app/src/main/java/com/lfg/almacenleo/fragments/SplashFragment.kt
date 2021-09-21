package com.lfg.almacenleo.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.lfg.almacenleo.R
import com.lfg.almacenleo.viewmodels.SplashViewModel

class SplashFragment : Fragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    private lateinit var viewModel: SplashViewModel
    private lateinit var v : View
    private val SPLASH_TIME_OUT:Long = 3000 // 3 sec

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_splash, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed(
            {
                val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                v.findNavController().navigate(action)
            }
            , SPLASH_TIME_OUT)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        // TODO: Use the ViewModel
    }

}