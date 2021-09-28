package com.lfg.miacell.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.lfg.miacell.databinding.FragmentLoginBinding
import com.lfg.miacell.functions.hideKeyboard
import com.lfg.miacell.viewmodels.LoginViewModel

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
        binding.txtUserName.setText("")
        binding.txtPassword.setText("")

        binding.btnLogin.setOnClickListener {
            this.hideKeyboard()
            var result = viewModel.login(binding.txtUserName.text.toString(), binding.txtPassword.text.toString())
            if (result == 0)
                navegateLoginSuccesful()
            else
                showMessage(result)
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