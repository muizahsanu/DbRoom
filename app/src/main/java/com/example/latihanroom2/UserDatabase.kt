package com.example.latihanroom2

import android.content.Context
import androidx.room.*
import com.example.latihanroom2.entities.UserDao
import com.example.latihanroom2.entities.UserEntity

@Database(entities = arrayOf(UserEntity::class),version = 2)
abstract class UserDatabase: RoomDatabase(){
    abstract fun UserDao(): UserDao

    companion object{
        var INSTANCES: UserDatabase?=null
        fun getIntance(context: Context): UserDatabase?{
            if(INSTANCES == null){
                synchronized(UserDatabase::class){
                    INSTANCES = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "UserDb"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCES
        }
        fun destroyIntances(){
            INSTANCES = null
        }
    }
}