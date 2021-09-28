package com.lfg.miacell.viewmodels

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.lfg.miacell.R
import com.lfg.miacell.database.AppDatabase
import com.lfg.miacell.database.UserDao
import com.lfg.miacell.databinding.FragmentLoginBinding
import com.lfg.miacell.entities.User
import kotlin.random.Random
import kotlin.random.nextInt

class RegisterViewModel : ViewModel() {
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null

    fun onCreateDB (context : Context) {
        db = AppDatabase.getAppDataBase(context)
        userDao = db?.UserDao()
    }

    fun checkNewUser (user : String, display : String, password : String) : Int
    {
        if (display.isEmpty())
            return R.string.not_user
        else if (user.isEmpty())
            return R.string.not_display
        else if (password.isEmpty())
            return R.string.not_password
        else
        {
            insertPerson(user, display, password)
            return 0
        }
    }

    fun insertPerson (user : String, display : String, password : String)
    {
        if (userDao?.loadPersonByUserName(user) == null)
           userDao?.insertPerson(User(Random.nextInt(1..10000000), user, display, password))
    }
}