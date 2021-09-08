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
import com.lfg.recyclerview.entities.User
import com.lfg.recyclerview.functions.hideKeyboard
import com.lfg.recyclerview.repositories.UserRepository
import com.lfg.recyclerview.viewmodels.NewUserViewModel

class NewUserFragment : Fragment() {

    companion object {
        fun newInstance() = NewUserFragment()
    }

    private lateinit var viewModelNewUser: NewUserViewModel
    lateinit var v : View
    lateinit var txtUser : TextView
    lateinit var txtPassword : TextView
    lateinit var btnCreate : Button
    lateinit var btnReturn : Button
    lateinit var frameLayout: ConstraintLayout
    private var userRepository = UserRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.create_user_fragment, container, false)
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
                var id = userRepository.userList.firstOrNull() { t -> t.user.toString().startsWith(txtUser.text) }
                if(id != null)
                {
                    Snackbar.make(frameLayout,R.string.msg_userexist,Snackbar.LENGTH_SHORT).show()
                }
                else{
                    userRepository.userList.add(User(txtUser.text.toString(),txtPassword.text.toString()))
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