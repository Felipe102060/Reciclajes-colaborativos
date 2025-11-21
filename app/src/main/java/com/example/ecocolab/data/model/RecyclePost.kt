package com.example.ecocolab.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "publicaciones")
data class RecyclePost(
    @PrimaryKey val id: String = "",
    val userId: String = "",
    val material: String = "",
    val cantidad: String = "",
    val estado: String = "pendiente"
)
