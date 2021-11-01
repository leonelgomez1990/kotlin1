package com.lfg.cameraxapp.viewmodels

import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.lfg.cameraxapp.activities.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContentProviderCompat.requireContext
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
typealias LumaListener = (luma: Double) -> Unit


class TakePhotoViewModel : ViewModel() {
    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    fun onCreateCam(activity : Activity, context: Context) {
        // Request camera permissions
        if (allPermissionsGranted(context)) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                activity, TakePhotoViewModel.REQUIRED_PERMISSIONS, TakePhotoViewModel.REQUEST_CODE_PERMISSIONS
            )
        }


        //outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    fun takePhoto() {}

    private fun startCamera() {}

    private fun allPermissionsGranted(baseContext : Context) = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    /*
    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }
    */

    fun camShutdown() {
        cameraExecutor.shutdown()
    }

    fun onRequestPermissionsResultCam(requestCode : Int ,context : Context) {
        if (requestCode == TakePhotoViewModel.REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted(context)) {
                startCamera()
            } else {
                Toast.makeText(context,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }



}