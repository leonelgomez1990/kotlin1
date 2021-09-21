package com.lfg.almacenleo.repositories

import com.lfg.almacenleo.entities.User

class UserRepository {
    private var userList : MutableList<User> = mutableListOf()

    init {
        userList.add(User("leo", "g"))
    }

    fun getList () : MutableList<User> {
        return userList
    }
}