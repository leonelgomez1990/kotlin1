package com.lfg.loginnav.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lfg.loginnav.R
import com.lfg.loginnav.entities.User
import com.lfg.loginnav.functions.hideKeyboard
import com.lfg.loginnav.repositories.UserRepository
import com.lfg.loginnav.viewmodels.Fragment3ViewModel

class Fragment3 : Fragment() {

    companion object {
        fun newInstance() = Fragment3()
    }

    private lateinit var viewModel: Fragment3ViewModel
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
        v = inflater.inflate(R.layout.fragment3_fragment, container, false)
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
        viewModel = ViewModelProvider(this).get(Fragment3ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}