package com.lfg.loginnav.repositories

import com.lfg.loginnav.entities.User

class UserRepository {
    var userList : MutableList<User> = mutableListOf()

    init {
        userList.add(User("Maxi", "morales"))
        userList.add(User("Jane", "Doe"))
        userList.add(User("Jessi", "Benja"))
    }
}