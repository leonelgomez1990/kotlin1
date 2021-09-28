package com.lfg.miacell.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lfg.miacell.R
import com.lfg.miacell.databinding.FragmentRegisterBinding
import com.lfg.miacell.functions.hideKeyboard
import com.lfg.miacell.viewmodels.RegisterViewModel

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var binding : FragmentRegisterBinding
    private val REGISTER_TIME_OUT:Long = 3000 // 3 sec

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        viewModel.onCreateDB(binding.layoutRegister.context)
        return binding.root
    }

    override fun onStart() {
        super.onStart()


        binding.btnBackLogin.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnNewUser.setOnClickListener {
            hideKeyboard()
            var result = viewModel.checkNewUser(binding.txtNewUser.text.toString(), binding.txtNewDisplay.text.toString(), binding.txtNewPassword.text.toString())
            if (result == 0) {
                showMessage(R.string.prompt_successful_newuser)
                Handler().postDelayed(
                    {
                        findNavController().popBackStack()
                    }
                    , REGISTER_TIME_OUT)
            }
            else {
                showMessage(result)
            }
        }
    }

    private fun showMessage(strMsg : Int) {
        Snackbar.make(binding.layoutRegister, strMsg, Snackbar.LENGTH_SHORT).show()
    }
}