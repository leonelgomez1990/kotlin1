package com.lfg.masterdetail.repositories

import com.lfg.masterdetail.entities.User

class UserRepository {
    private var userList : MutableList<User> = mutableListOf()

    init {
        userList.add(User("Maxi", "morales"))
        userList.add(User("Jane", "Doe"))
        userList.add(User("Jessi", "Benja"))
        userList.add(User("leo", "g"))
    }

    fun getList () : MutableList<User> {
        return userList
    }
}