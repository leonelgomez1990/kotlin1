package com.lfg.miacell.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lfg.miacell.databinding.FragmentLoginBinding
import com.lfg.miacell.functions.hideKeyboard
import com.lfg.miacell.viewmodels.LoginViewModel
import android.widget.Toast
import com.lfg.miacell.R


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        viewModel.onCreateDB(binding.layoutLogin.context)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStartUser(requireContext())
        binding.txtUserName.setText(viewModel.user?.user)
        binding.txtPassword.setText(viewModel.user?.password)
        binding.cbxRemember.isChecked = viewModel.memChecked
        binding.chkAutoLogin.isChecked = viewModel.memAutoLogin
        if(viewModel.memChecked && viewModel.memAutoLogin)
        {
            val toast1 = Toast.makeText(
                requireContext(),
                getString(R.string.txt_delay_autologin), Toast.LENGTH_SHORT
            )

            toast1.show()
            Handler().postDelayed(
                {
                    binding.btnLogin.performClick()
                }
                , 1000)

        }

        binding.btnLogin.setOnClickListener {
            this.hideKeyboard()
            var result = viewModel.login(requireContext(), binding.txtUserName.text.toString(), binding.txtPassword.text.toString())
            if (result == 0)
                navegateLoginSuccesful()
            else
                showMessage(result)
        }

        binding.btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            binding.layoutLogin.findNavController().navigate(action)
        }

        binding.cbxRemember.setOnCheckedChangeListener { _, isChecked ->
            viewModel.checkedRemember(requireContext(), isChecked)
        }

        binding.chkAutoLogin.setOnCheckedChangeListener { _, isChecked ->
            viewModel.checkedAutoLogin(requireContext(), isChecked)
        }
    }

    private fun showMessage(strMsg : Int) {
        Snackbar.make(binding.layoutLogin, strMsg, Snackbar.LENGTH_SHORT).show()
    }

    private fun navegateLoginSuccesful(){
        val action = LoginFragmentDirections.actionLoginFragmentToMainActivity()
        binding.layoutLogin.findNavController().navigate(action)
    }

}