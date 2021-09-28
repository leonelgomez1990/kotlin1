package com.lfg.miacell.viewmodels

import android.content.Context
import android.content.SharedPreferences
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
    private val PREF_NAME = "myUser"
    var memChecked = false
    var memAutoLogin = false

    fun onCreateDB (context : Context) {
        db = AppDatabase.getAppDataBase(context)
        userDao = db?.UserDao()
    }

    fun onStartUser (context : Context) {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        memAutoLogin = sharedPref.getBoolean("AUTOLOGIN",false)!!
        memChecked = sharedPref.getBoolean("REMEMBER",false)!!
        if (memChecked)
            this.user = User(sharedPref.getInt("ID",0), sharedPref.getString("USER","")!!, sharedPref.getString("DISPLAY","")!!, sharedPref.getString("PASSWORD","")!!)
        else
            this.user = User(0, "", "", "")

    }


    fun logout () {
        user = null
    }

    fun login (context : Context, username : String, password : String) : Int {
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
                    val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()

                    editor.putInt("ID", foundUser.id)
                    editor.putString("USER", foundUser.user)
                    editor.putString("DISPLAY", foundUser.display)
                    editor.putString("PASSWORD", foundUser.password)
                    editor.apply()

                    this.user = User(foundUser.id, foundUser.user, foundUser.display, foundUser.password)
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

    fun insertPerson (user : String, display : String, password : String) : Boolean
    {
        if (userDao?.loadPersonByUserName(user) == null)
        {
            userDao?.insertPerson(User(Random.nextInt(1..10000000), user, display, password))
            return true
        }
        else
            return false
    }

    fun checkedRemember (context : Context, checked : Boolean)
    {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putBoolean("REMEMBER", checked)
        editor.apply()
        memChecked = checked

    }
    fun checkedAutoLogin (context : Context, checked : Boolean)
    {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putBoolean("AUTOLOGIN", checked)
        editor.apply()
        memAutoLogin = checked

    }
}