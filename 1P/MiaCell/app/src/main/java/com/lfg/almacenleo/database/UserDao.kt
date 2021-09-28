package com.lfg.almacenleo.database

import androidx.room.*
import com.lfg.almacenleo.entities.User

@Dao
public interface UserDao {
    @Query("SELECT * FROM users ORDER BY id")
    fun loadAllPersons(): MutableList<User?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(user: User?)

    @Update
    fun updatePerson(user: User?)

    @Delete
    fun delete(user: User?)

    @Query("SELECT * FROM users WHERE id = :id")
    fun loadPersonById(id: Int): User?

    @Query("SELECT * FROM users WHERE user = :user")
    fun loadPersonByUserName(user: String): User?
}