package com.lfg.miacell.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.lfg.miacell.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var binding : FragmentSplashBinding
    private val SPLASH_TIME_OUT:Long = 3000 // 3 sec

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed(
            {
                val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                this.view?.findNavController()?.navigate(action)
            }
            , SPLASH_TIME_OUT)
    }

}