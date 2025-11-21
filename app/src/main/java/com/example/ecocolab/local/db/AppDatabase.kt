package com.example.ecocolab.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecocolab.entities.PostEntity
import com.example.ecocolab.entities.UserEntity

@Database(
    entities = [UserEntity::class, PostEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun recyclePostDao(): RecyclePostDao
}
