package com.example.ecocolab.entities
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val nombre: String,
    val email: String,
    val tipo: String,
    val puntos: Int
)