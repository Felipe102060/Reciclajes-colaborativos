package com.example.ecocolab.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String = "",
    val nombre: String = "",
    val email: String = "",
    val tipo: String = "",
    var puntos: Int = 0
)
