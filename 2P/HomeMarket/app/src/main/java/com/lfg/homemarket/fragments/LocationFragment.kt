package com.lfg.homemarket.fragments

import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.lfg.homemarket.R
import com.lfg.homemarket.clases.LocationHelper
import com.lfg.homemarket.databinding.LocationFragmentBinding
import com.lfg.homemarket.viewmodels.LocationViewModel
import android.text.Editable

import android.text.TextWatcher




class LocationFragment : Fragment() {

    companion object {
        fun newInstance() = LocationFragment()
    }

    private val viewModel: LocationViewModel by viewModels()
    private lateinit var binding : LocationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LocationFragmentBinding.inflate(layoutInflater)
        viewModel.getStoredCoordinates(requireContext())
        return binding.root
    }

    private fun onGettingLocation(lat : String, long : String ) {
        //lat = "-34.713078"
        //long = "-58.497269"
        if(viewModel.askForLocation(lat, long))
            showMessage(getString(R.string.location_msg_get_loc_ok))
        else
            showMessage(getString(R.string.location_msg_get_loc_error))

    }


    override fun onStart() {
        super.onStart()

        binding.btnLocationGetLoc.setOnClickListener {
            val locationclass = LocationHelper(requireActivity(),requireContext()) {
                    lat, long -> onGettingLocation(lat, long)
            }
        }

        binding.btnLocationSaveLoc.setOnClickListener {
            if(viewModel.setCoordinates(requireContext(), binding.txtLocationLat.text.toString(), binding.txtLocationLong.text.toString()))
                showMessage(getString(R.string.location_msg_save_loc_ok))
            else
                showMessage(getString(R.string.location_msg_save_loc_error))
        }

        binding.txtLocationLat.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                getLocationAddress()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
        })
        binding.txtLocationLong.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                getLocationAddress()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
        })


        viewModel.latitud.observe(viewLifecycleOwner, { result ->
            binding.txtLocationLat.setText(result.toString())
            getLocationAddress()
        })
        viewModel.longitud.observe(viewLifecycleOwner, { result ->
            binding.txtLocationLong.setText(result.toString())
            getLocationAddress()
        })

    }

    private fun getLocationAddress() {
        try {
            val address = getAddress(binding.txtLocationLat.text.toString().toDouble(), binding.txtLocationLong.text.toString().toDouble())
            binding.txtLocationAddress.text = address
        }
        catch (ex : Exception) {}
    }

    private fun showMessage(str : String) {
        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
    }

    private fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(requireContext())
        val list = geocoder.getFromLocation(lat, lng, 1)
        return list[0].getAddressLine(0)
    }
}