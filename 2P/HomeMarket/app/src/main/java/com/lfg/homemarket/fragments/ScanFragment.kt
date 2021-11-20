package com.lfg.homemarket.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.lfg.homemarket.R
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
    private var activityResultLauncher: ActivityResultLauncher<Array<String>>

    init{
        this.activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { result ->
            var allAreGranted = true
            for(b in result.values)
                allAreGranted = allAreGranted && b

            if(allAreGranted)
                viewModel.cameraHelper.start()
            else
                showMessage(getString(R.string.camera_msg_not_permission))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ScanFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityResultLauncher.launch(CameraHelper.REQUIRED_PERMISSIONS)
    }

    override fun onStart() {
        super.onStart()
        viewModel.scannedId.value = ""
        viewModel.flashState = false

        viewModel.cameraHelper = CameraHelper(
            owner = activity as AppCompatActivity,
            context = requireContext(),
            viewFinder = binding.cameraView,
            onResult = ::onResult
        )
        viewModel.cameraHelper.start()

        viewModel.scannedId.observe(viewLifecycleOwner, { result ->
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
            val action = ScanFragmentDirections.actionScanFragmentToListFragment()
            binding.viewScanFragment.findNavController().navigate(action)
         }

        binding.btnTorchMode.setOnClickListener {
            viewModel.flashState = !viewModel.flashState
            binding.btnTorchMode.isSelected = viewModel.flashState
            if(!viewModel.cameraHelper.setTorchMode(viewModel.flashState)) {
                viewModel.flashState = false
                binding.btnTorchMode.isSelected = viewModel.flashState
                showMessage(getString(R.string.torch_msg_get_cam_error))
            }
        }

    }

    override fun onStop() {
        super.onStop()
        viewModel.cameraHelper.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cameraHelper.stop()
    }

    private fun onResult(result: String) {
        Log.d(TAG, "Result is $result")
        viewModel.scannedId.value = result
    }

    private fun showMessage(str : String) {
        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
    }

}