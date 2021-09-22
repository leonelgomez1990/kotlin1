package com.lfg.almacenleo.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.lfg.almacenleo.R
import com.lfg.almacenleo.databinding.FragmentSplashBinding
import com.lfg.almacenleo.viewmodels.SplashViewModel

class SplashFragment : Fragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    private val viewModel: SplashViewModel by viewModels()
    private lateinit var binding : FragmentSplashBinding
    private val SPLASH_TIME_OUT:Long = 3000 // 3 sec

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed(
            {
                val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                binding.frameLayout2.findNavController().navigate(action)
            }
            , SPLASH_TIME_OUT)
    }

}