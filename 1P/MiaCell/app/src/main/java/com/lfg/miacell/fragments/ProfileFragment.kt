package com.lfg.miacell.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lfg.miacell.activities.SettingsActivity
import com.lfg.miacell.databinding.FragmentProfileBinding
import com.lfg.miacell.viewmodels.ProfileViewModel

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val viewModel : ProfileViewModel by viewModels()
    private lateinit var binding : FragmentProfileBinding
    private val PREF_NAME = "myUser"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val user = sharedPref.getString("USER","default")!!
        val display = sharedPref.getString("DISPLAY","default")!!

        binding.txtProfileDisplay.setText(display)
        binding.txtProfileUser.setText(user)

        binding.btnLogout.setOnClickListener {
            val editor = sharedPref.edit()
            editor.putBoolean("AUTOLOGIN", false)
            editor.apply()
            activity?.finish()
        }

        binding.btnSettings.setOnClickListener {
            activity?.startActivity(Intent(this.requireContext(),SettingsActivity::class.java))
        }
    }

}