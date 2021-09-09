package com.lfg.masterdetail.fragments

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
import com.lfg.masterdetail.R
import com.lfg.masterdetail.functions.hideKeyboard
import com.lfg.masterdetail.repositories.UserRepository
import com.lfg.masterdetail.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModelLogin: LoginViewModel
    private lateinit var v : View
    private lateinit var btnLogin : Button
    private lateinit var txtUser : TextView
    private lateinit var txtPassword : TextView
    private lateinit var frameLayout : ConstraintLayout
    private var userRepository = UserRepository()
    private lateinit var btnCreate : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.login_fragment, container, false)
        //Binding
        btnLogin = v.findViewById(R.id.btnLogin)
        txtUser = v.findViewById(R.id.txtUser)
        txtPassword = v.findViewById(R.id.txtPassword)
        frameLayout = v.findViewById(R.id.frameLayout1)
        btnCreate = v.findViewById(R.id.btnCreate)

        return v
    }

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        //cuando giro la pantalla
//    }

    override fun onStart() {
        super.onStart()
        txtUser.text = ""
        txtPassword.text = ""

        btnLogin.setOnClickListener {
            this.hideKeyboard()
            if(txtUser.length() <= 0) {
                Snackbar.make(frameLayout, R.string.msg_empty, Snackbar.LENGTH_SHORT).show()
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
                        Snackbar.make(frameLayout, R.string.msg_wrongpassword, Snackbar.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    Snackbar.make(frameLayout, R.string.msg_wronguser, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        btnCreate.setOnClickListener {
            navegateCreateNewUser()
        }
    }

    private fun navegateLoginSuccesful(){
        val action = LoginFragmentDirections.actionFragment1ToMainActivity2()
        v.findNavController().navigate(action)
    }
    private fun navegateCreateNewUser(){
        val action2 = LoginFragmentDirections.actionFragment1ToFragment3()
        v.findNavController().navigate(action2)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelLogin = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}