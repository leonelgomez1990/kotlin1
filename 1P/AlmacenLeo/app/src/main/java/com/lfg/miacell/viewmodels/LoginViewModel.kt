package com.lfg.miacell.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.lfg.miacell.R
import com.lfg.miacell.database.AppDatabase
import com.lfg.miacell.database.UserDao
import com.lfg.miacell.entities.User
//import com.lfg.almacenleo.repositories.UserRepository
import kotlin.random.Random
import kotlin.random.nextInt

class LoginViewModel : ViewModel() {
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    var user: User? = null
    //private var userRepository = UserRepository()

    fun onCreateDB (context : Context) {
        db = AppDatabase.getAppDataBase(context)
        userDao = db?.UserDao()
        userDao?.insertPerson(User(Random.nextInt(1..10000000), "leo", "Leonel", "a"))
    }

    fun logout () {
        user = null
    }

    fun login (username : String, password : String) : Int {
        if(username.isEmpty()) {
            return R.string.invalid_emptyuser
        }
        else
        {
            val foundUser = userDao?.loadPersonByUserName(username)
            //val foundUser = userRepository.getList().firstOrNull { t -> t.user == username }
            if(foundUser != null)
            {
                //chequeo contraseña
                if (password == foundUser.password)
                {
                    this.user = User(0,username, username, password)
                    return 0
                }
                else
                {
                    return R.string.invalid_password
                }
            }
            else
            {
                return R.string.invalid_username
            }
        }
    }

}