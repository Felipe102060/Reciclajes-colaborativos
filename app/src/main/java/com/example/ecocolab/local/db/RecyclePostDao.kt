package com.example.ecocolab.local.db

import androidx.room.*
import com.example.ecocolab.entities.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecyclePostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity)

    @Query("SELECT * FROM posts")
    fun getAllPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE id = :id LIMIT 1")
    suspend fun findById(id: String): PostEntity?

    @Update
    suspend fun updatePost(post: PostEntity)

    @Query("UPDATE posts SET estado = :estado WHERE id = :id")
    suspend fun updateEstado(id: String, estado: String)
}
