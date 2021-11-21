package com.lfg.homemarket.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
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
    private lateinit var activityResultLauncher: ActivityResultLauncher<Array<String>>

    init{
        onInitActivityResultRegistration()
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

        askForCameraPermissions()
    }

    override fun onStart() {
        super.onStart()
        viewModel.scannedId.value = ""
        viewModel.flashState = false

        viewModel.cameraHelper = CameraHelper(
            owner = activity as AppCompatActivity,
            context = requireContext(),
            viewFinder = binding.cameraView,
            onResult = ::onBarScannedResult
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

    private fun onBarScannedResult(result: String) {
        Log.d(TAG, "Result is $result")
        viewModel.scannedId.value = result
    }

    private fun showMessage(str : String) {
        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
    }

    private fun onInitActivityResultRegistration() = activityResultRegistration {
            result -> actionPermissionForCamera(result)
    }

    private fun activityResultRegistration(actionWhenRequestPermissionIsFinished : (Boolean) -> Unit) {
        this.activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { result ->
            var allPermissionsAreGranted = true
            for(b in result.values)
                allPermissionsAreGranted = allPermissionsAreGranted && b

            if(allPermissionsAreGranted) {
                actionWhenRequestPermissionIsFinished(true)
            } else actionWhenRequestPermissionIsFinished(false)
        }
    }

    private fun actionPermissionForCamera(result : Boolean) {
        if(result) {
            viewModel.cameraHelper.start()
        }
        else {
            showCustomSnackBar(getString(R.string.camera_msg_not_permission), getString(R.string.msg_action_retry)) {
                askForCameraPermissions()
            }
        }
    }

    private fun askForCameraPermissions() = this.activityResultLauncher.launch(CameraHelper.REQUIRED_PERMISSIONS)

    private fun showCustomSnackBar(
        msgText: String,
        actionText: String,
        onActionClick : (Unit) -> Unit
    ) {
        val snackBar = Snackbar.make(
            binding.viewScanFragment,
            msgText,
            Snackbar.LENGTH_LONG
        ).setAction(
            actionText
        ) {
            onActionClick(Unit)
        }
        //setting action text color o red
        snackBar.setActionTextColor(resources.getColor(R.color.red))
        val sbView = snackBar.view
        //getting the textview of the snackbar
        val textView =
            sbView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        //setting snackbar text color to green
        textView.setTextColor(resources.getColor(R.color.green))
        snackBar.show()
    }
}