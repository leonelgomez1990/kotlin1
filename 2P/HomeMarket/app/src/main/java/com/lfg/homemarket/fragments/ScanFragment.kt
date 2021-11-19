package com.lfg.homemarket.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.lfg.homemarket.clases.CameraHelper
import com.lfg.homemarket.databinding.ScanFragmentBinding
import com.lfg.homemarket.viewmodels.ScanViewModel

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
        viewModel.scannedId.value = ""

        viewModel.cameraHelper = CameraHelper(
            owner = activity as AppCompatActivity,
            context = requireContext(),
            viewFinder = binding.cameraView,
            onResult = ::onResult
        )
        viewModel.cameraHelper.start()

        viewModel.scannedId.observe(viewLifecycleOwner, Observer { result ->
            binding.scanTextResult.text = result.toString()
            if(result == "")
                binding.btnListAdd.hide()
            else
                binding.btnListAdd.show()
        })

        binding.btnListAdd.setOnClickListener {
            if(viewModel.scannedId.value.toString() != "")
            viewModel.saveScannedId(requireContext(), viewModel.scannedId.value.toString())
            //Navegar
            val action = ScanFragmentDirections.actionScanFragmentToListFragment(viewModel.scannedId.value.toString())
            binding.viewScanFragment.findNavController().navigate(action)
         }

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cameraHelper.stop()
    }

    private fun onResult(result: String) {
        Log.d(TAG, "Result is $result")
        viewModel.scannedId.value = result
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        viewModel.cameraHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}