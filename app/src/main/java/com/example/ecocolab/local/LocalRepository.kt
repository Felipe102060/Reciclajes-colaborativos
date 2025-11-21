package com.example.ecocolab.local

import android.content.Context
import androidx.room.Room
import com.example.ecocolab.entities.PostEntity
import com.example.ecocolab.entities.UserEntity
import com.example.ecocolab.local.db.AppDatabase
import kotlinx.coroutines.flow.Flow

class LocalRepository private constructor(context: Context) {

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "ecocolab.db"
    ).fallbackToDestructiveMigration().build()

    private val userDao = db.userDao()
    private val postDao = db.recyclePostDao()

    // -------------------- USUARIOS --------------------

    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun findUserByEmail(email: String): UserEntity? {
        return userDao.findByEmail(email)
    }

    // ---------------- PUBLICACIONES --------------------

    suspend fun insertPost(post: PostEntity) {
        postDao.insertPost(post)
    }

    fun getAllPostsFlow(): Flow<List<PostEntity>> {
        return postDao.getAllPosts()
    }

    suspend fun findPostById(id: String): PostEntity? {
        return postDao.findById(id)
    }

    suspend fun updatePost(post: PostEntity) {
        postDao.updatePost(post)
    }

    suspend fun updateEstado(id: String, estado: String) {
        postDao.updateEstado(id, estado)
    }

    companion object {
        @Volatile
        private var INSTANCE: LocalRepository? = null

        fun getInstance(context: Context): LocalRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocalRepository(context).also { INSTANCE = it }
            }
        }
    }
}
