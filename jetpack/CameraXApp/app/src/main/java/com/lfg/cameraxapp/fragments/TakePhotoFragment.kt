package com.lfg.cameraxapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lfg.cameraxapp.R
import com.lfg.cameraxapp.databinding.TakePhotoFragmentBinding
import com.lfg.cameraxapp.viewmodels.TakePhotoViewModel
import java.io.File

class TakePhotoFragment : Fragment() {

    companion object {
        fun newInstance() = TakePhotoFragment()
    }

    private val viewModel: TakePhotoViewModel by viewModels()
    private lateinit var binding : TakePhotoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TakePhotoFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.onCreateCam(requireActivity(),requireContext())

        viewModel.viewFnd = binding.viewFinder
        viewModel.lifecyc = this
        viewModel.outputDirectory = getOutputDirectory()

        // Set up the listener for take photo button
        binding.camCaptureButton.setOnClickListener { viewModel.takePhoto(requireContext()) }

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.camShutdown()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        onRequestPermissionsResult(requestCode, permissions, grantResults)
        viewModel.onRequestPermissionsResultCam(requestCode, requireContext())
    }

    private fun getOutputDirectory(): File {
        val mediaDir = activity?.externalMediaDirs?.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else activity?.filesDir!!
    }
}