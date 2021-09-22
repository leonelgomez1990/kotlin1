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
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lfg.almacenleo.R
import com.lfg.almacenleo.functions.hideKeyboard
import com.lfg.almacenleo.repositories.UserRepository
import com.lfg.almacenleo.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var v : View
    private lateinit var btnLogin : Button
    private lateinit var txtUser : TextView
    private lateinit var txtPassword : TextView
    private lateinit var frameLayout : ConstraintLayout
    private var userRepository = UserRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_login, container, false)
        //Binding
        btnLogin = v.findViewById(R.id.btnLogin)
        txtUser = v.findViewById(R.id.txtUser)
        txtPassword = v.findViewById(R.id.txtPassword)
        frameLayout = v.findViewById(R.id.layoutLogin)

        return v
    }

    override fun onStart() {
        super.onStart()
        txtUser.text = ""
        txtPassword.text = ""

        btnLogin.setOnClickListener {
            this.hideKeyboard()
            if(txtUser.length() <= 0) {
                Snackbar.make(frameLayout, R.string.invalid_username, Snackbar.LENGTH_SHORT).show()
            }
            else
            {
                val id = userRepository.getList().firstOrNull { t -> t.user == txtUser.text.toString() }
                if(id != null)
                {
                    //chequeo contraseña
                    if (txtPassword.text.toString() == id.password)
                    {
                        navegateLoginSuccesful()
                    }
                    else
                    {
                        Snackbar.make(frameLayout, R.string.invalid_password, Snackbar.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    Snackbar.make(frameLayout, R.string.invalid_username, Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun navegateLoginSuccesful(){
        val action = LoginFragmentDirections.actionLoginFragmentToMainActivity()
        v.findNavController().navigate(action)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}