package com.lfg.homemarket.fragments

import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.lfg.homemarket.R
import com.lfg.homemarket.clases.LocationHelper
import com.lfg.homemarket.databinding.LocationFragmentBinding
import com.lfg.homemarket.viewmodels.LocationViewModel


class LocationFragment : Fragment() {

    companion object {
        fun newInstance() = LocationFragment()
    }

    private val viewModel: LocationViewModel by viewModels()
    private lateinit var binding : LocationFragmentBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Array<String>>

    init{
        onInitActivityResultRegistration()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LocationFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLocationGetLoc.setOnClickListener {
            askForLocationPermissions()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getStoredCoordinates(requireContext())

        binding.btnLocationSaveLoc.setOnClickListener {
            if(viewModel.saveLocationValue(binding.txtLocationLat.text.toString(), binding.txtLocationLong.text.toString())) {
                viewModel.setStoredCoordinates(requireContext())
                showMessage(getString(R.string.location_msg_get_loc_ok))
            }
            else
                showMessage(getString(R.string.location_msg_get_loc_error))
        }

        binding.txtLocationLat.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                getStringAddressFromLocation()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
        })
        binding.txtLocationLong.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                getStringAddressFromLocation()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
        })


        viewModel.latitud.observe(viewLifecycleOwner, { result ->
            binding.txtLocationLat.setText(result.toString())
            getStringAddressFromLocation()
        })
        viewModel.longitud.observe(viewLifecycleOwner, { result ->
            binding.txtLocationLong.setText(result.toString())
            getStringAddressFromLocation()
        })

    }

    private fun showMessage(str : String) {
        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
    }

    private fun onInitActivityResultRegistration() = activityResultRegistration {
        result -> actionPermissionForLocation(result)
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

    private fun actionPermissionForLocation(result : Boolean) {
        if(result) {
            LocationHelper(activity as AppCompatActivity,requireContext()) {
                    lat, long -> onGettingLocation(lat, long)
            }
        }
        else {
            showCustomSnackBar(getString(R.string.location_msg_not_permission), getString(R.string.location_msg_action_retry)) {
                askForLocationPermissions()
            }
        }
    }

    private fun askForLocationPermissions() = this.activityResultLauncher.launch(LocationHelper.REQUIRED_PERMISSIONS)

    private fun onGettingLocation(lat : String, long : String ) {
        if(viewModel.saveLocationValue(lat, long))
            showMessage(getString(R.string.location_msg_get_loc_ok))
        else
            showMessage(getString(R.string.location_msg_get_loc_error))
    }

    private fun getStringAddressFromLocation() {
        if((binding.txtLocationLat.text.toString() != "") && (binding.txtLocationLong.text.toString() != ""))
        {
            try {
                val address = getAddress(binding.txtLocationLat.text.toString().toDouble(), binding.txtLocationLong.text.toString().toDouble())
                binding.txtLocationAddress.text = address
            }
            catch (ex : Exception) {
                showMessage(getString(R.string.location_msg_get_loc_error))
            }
        }
    }

    private fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(requireContext())
        val list = geocoder.getFromLocation(lat, lng, 1)
        return list[0].getAddressLine(0)
    }

    private fun showCustomSnackBar(
        msgText: String,
        actionText: String,
        onActionClick : (Unit) -> Unit
    ) {
        val snackBar = Snackbar.make(
            binding.viewProfileFragment,
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