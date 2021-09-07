package com.lfg.recyclerview.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lfg.recyclerview.R
import com.lfg.recyclerview.functions.hideKeyboard
import com.lfg.recyclerview.repositories.UserRepository
import com.lfg.recyclerview.viewmodels.Fragment1ViewModel

class Fragment1 : Fragment() {

    companion object {
        fun newInstance() = Fragment1()
    }

    private lateinit var viewModel: Fragment1ViewModel
    lateinit var v : View
    lateinit var btnLogin : Button
    lateinit var txtUser : TextView
    lateinit var txtPassword : TextView
    lateinit var frameLayout : ConstraintLayout
    private var userRepository = UserRepository()
    lateinit var btnCreate : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment1_fragment, container, false)
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
        btnLogin.setOnClickListener {
            this.hideKeyboard()
            if(txtUser.length() <= 0) {
                Snackbar.make(frameLayout, R.string.msg_empty, Snackbar.LENGTH_SHORT).show()
            }
            else
            {
                var id = userRepository.userList.firstOrNull() { t -> t.user.toString().startsWith(txtUser.text) }
                if(id != null)
                {
                    //chequeo contraseña
                    if (txtPassword.text.toString() == id.password.toString())
                    {
                        navegar(txtUser.text.toString())
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
            val action2 = Fragment1Directions.actionFragment1ToFragment3()
            findNavController().navigate(action2)
        }
    }

    fun navegar(user : String){
        val action = Fragment1Directions.actionFragment1ToNavgraph2()
        findNavController().navigate(action)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Fragment1ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}