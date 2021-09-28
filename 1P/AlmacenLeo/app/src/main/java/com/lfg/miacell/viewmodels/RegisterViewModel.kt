package com.lfg.miacell.viewmodels

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.lfg.miacell.R
import com.lfg.miacell.databinding.FragmentLoginBinding

class RegisterViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val vMLogin : LoginViewModel = LoginViewModel()

    fun checkNewUser (user : String, display : String, password : String) : Int
    {
        if (user.isEmpty())
            return R.string.not_user
        else if (display.isEmpty())
            return R.string.not_display
        else if (password.isEmpty())
            return R.string.not_password
        else
        {
            vMLogin.insertPerson(user, display, password)
            return 0
        }
    }
}