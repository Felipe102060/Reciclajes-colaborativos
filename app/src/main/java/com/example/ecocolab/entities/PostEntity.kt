package com.example.ecocolab.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val material: String,
    val cantidad: String,
    val estado: String
)