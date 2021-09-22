package com.lfg.almacenleo.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lfg.almacenleo.R
import com.lfg.almacenleo.databinding.FragmentLoginBinding
import com.lfg.almacenleo.functions.hideKeyboard
import com.lfg.almacenleo.repositories.UserRepository
import com.lfg.almacenleo.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding : FragmentLoginBinding
    private var userRepository = UserRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.txtUser.setText("")
        binding.txtPassword.setText("")

        binding.btnLogin.setOnClickListener {
            this.hideKeyboard()
            if(binding.txtUser.length() <= 0) {
                Snackbar.make(binding.layoutLogin, R.string.invalid_emptyuser, Snackbar.LENGTH_SHORT).show()
            }
            else
            {
                val id = userRepository.getList().firstOrNull { t -> t.user == binding.txtUser.text.toString() }
                if(id != null)
                {
                    //chequeo contraseña
                    if (binding.txtPassword.text.toString() == id.password)
                    {
                        navegateLoginSuccesful()
                    }
                    else
                    {
                        Snackbar.make(binding.layoutLogin, R.string.invalid_password, Snackbar.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    Snackbar.make(binding.layoutLogin, R.string.invalid_username, Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun navegateLoginSuccesful(){
        val action = LoginFragmentDirections.actionLoginFragmentToMainActivity()
        binding.layoutLogin.findNavController().navigate(action)
    }

}