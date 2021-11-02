package com.lfg.barcodescanning.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.lfg.barcodescanning.clases.CameraHelper
import com.lfg.barcodescanning.databinding.ScanFragmentBinding
import com.lfg.barcodescanning.viewmodels.ScanViewModel

class ScanFragment : Fragment() {

    companion object {
        fun newInstance() = ScanFragment()
        const val TAG = "CameraXDemo"
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

    override fun onStart() {
        super.onStart()

        viewModel.cameraHelper = CameraHelper(
            owner = activity as AppCompatActivity,
            context = requireContext(),
            viewFinder = binding.cameraView,
            onResult = ::onResult
        )
        viewModel.cameraHelper.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cameraHelper.stop()
    }

    private fun onResult(result: String) {
        Log.d(TAG, "Result is $result")
        binding.textResult.text = result
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        viewModel.cameraHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}