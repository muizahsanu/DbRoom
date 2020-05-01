package com.example.latihanroom2.entities

import androidx.room.*

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var nama: String,
    var email: String,
    var zona: String
)