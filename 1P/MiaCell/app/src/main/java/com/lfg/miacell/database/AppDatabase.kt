package com.lfg.miacell.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lfg.miacell.entities.User

//entities = [User::class,Product::class]
@Database(entities = [User::class], version = 1, exportSchema = false)

public  abstract class AppDatabase : RoomDatabase() {

    abstract fun UserDao(): UserDao
    //abstract fun ProductDao(): ProductDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "myDB"
                    ).allowMainThreadQueries().build() // No es lo mas recomendable que se ejecute en el mainthread
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}