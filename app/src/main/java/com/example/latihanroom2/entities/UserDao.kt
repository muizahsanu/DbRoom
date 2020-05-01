package com.example.latihanroom2.entities

import androidx.room.*

@Dao
interface UserDao {
    @Insert()
    fun tambahData(userEntity: UserEntity)
    @Query("select * from users")
    fun getDataUser(): List<UserEntity>
    @Query("delete from users")
    fun deleteAllUsers()
    @Query("select * from users where zona like :zona")
    fun getDataUserByZona(zona: String): List<UserEntity>
}