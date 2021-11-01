package com.lfg.barcodescanning.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lfg.barcodescanning.databinding.ScanFragmentBinding
import com.lfg.barcodescanning.viewmodels.ScanViewModel

class ScanFragment : Fragment() {

    companion object {
        fun newInstance() = ScanFragment()
    }

    private val viewModel: ScanViewModel by viewModels()
    private lateinit var binding : ScanFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ScanFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

}