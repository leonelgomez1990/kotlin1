package com.lfg.miacell.fragments

import android.content.Intent
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

    private val viewModel : ProfileViewModel by viewModels()
    private lateinit var binding : FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStartProfile(requireContext())

        viewModel.display.observe(viewLifecycleOwner, { result ->
            binding.txtProfileDisplay.text = result.toString()
        })
        viewModel.user.observe(viewLifecycleOwner, { result ->
            binding.txtProfileUser.text = result.toString()
        })
        binding.btnLogout.setOnClickListener {
            viewModel.setAutoLoginFalse()
            activity?.finish()
        }

        binding.btnSettings.setOnClickListener {
            activity?.startActivity(Intent(this.requireContext(),SettingsActivity::class.java))
        }
    }

}