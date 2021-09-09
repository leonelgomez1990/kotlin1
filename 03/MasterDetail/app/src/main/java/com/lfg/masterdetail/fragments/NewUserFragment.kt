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
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lfg.masterdetail.R
import com.lfg.masterdetail.entities.User
import com.lfg.masterdetail.functions.hideKeyboard
import com.lfg.masterdetail.repositories.UserRepository
import com.lfg.masterdetail.viewmodels.NewUserViewModel

class NewUserFragment : Fragment() {

    companion object {
        fun newInstance() = NewUserFragment()
    }

    private lateinit var viewModelNewUser: NewUserViewModel
    private lateinit var v : View
    private lateinit var txtUser : TextView
    private lateinit var txtPassword : TextView
    private lateinit var btnCreate : Button
    private lateinit var btnReturn : Button
    private lateinit var frameLayout: ConstraintLayout
    private var userRepository = UserRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.create_user_fragment, container, false)
        //Binding
        txtUser = v.findViewById(R.id.txtUser2)
        txtPassword = v.findViewById(R.id.txtPassword2)
        btnCreate = v.findViewById(R.id.btnCreate2)
        btnReturn = v.findViewById(R.id.btnReturn)
        frameLayout = v.findViewById(R.id.frameLayout3)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnCreate.setOnClickListener {
            this.hideKeyboard()

            if((txtUser.text != "") && (txtPassword.text != "")){
                val id = userRepository.getList().firstOrNull { t -> t.user == txtUser.text.toString() }
                if(id != null)
                {
                    Snackbar.make(frameLayout,R.string.msg_userexist,Snackbar.LENGTH_SHORT).show()
                }
                else{
                    userRepository.getList().add(User(txtUser.text.toString(),txtPassword.text.toString()))
                    Snackbar.make(frameLayout,R.string.msg_usercreated,Snackbar.LENGTH_LONG).show()
                    txtUser.text = ""
                    txtPassword.text = ""
                }
            }
            else{
                Snackbar.make(frameLayout,R.string.msg_complete,Snackbar.LENGTH_SHORT).show()
            }
        }
        btnReturn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelNewUser = ViewModelProvider(this).get(NewUserViewModel::class.java)
        // TODO: Use the ViewModel
    }

}