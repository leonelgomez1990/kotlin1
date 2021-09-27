package com.lfg.almacenleo.repositories

import com.lfg.almacenleo.entities.User
import kotlin.random.Random
import kotlin.random.nextInt

class UserRepository {
    private var userList : MutableList<User> = mutableListOf()

    init {
        userList.add(User(Random.nextInt(1..10000000), "leo", "leo", "g"))
    }

    fun getList () : MutableList<User> {
        return userList
    }
}