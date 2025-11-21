package com.example.ecocolab.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecocolab.data.model.RecyclePost
import com.example.ecocolab.data.model.User
import com.example.ecocolab.entities.PostEntity
import com.example.ecocolab.entities.UserEntity
import com.example.ecocolab.local.LocalRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = LocalRepository.getInstance(application.applicationContext)

    // Estados observables
    val usuarioActual = mutableStateOf<User?>(null)
    val listaPublicaciones = mutableStateOf<List<RecyclePost>>(emptyList())

    init {
        // Suscribirse a los posts guardados en Room
        viewModelScope.launch {
            repo.getAllPostsFlow().collectLatest { list ->
                listaPublicaciones.value = list.map { pe ->
                    RecyclePost(
                        id = pe.id,
                        userId = pe.userId,
                        material = pe.material,
                        cantidad = pe.cantidad,
                        estado = pe.estado
                    )
                }
            }
        }
    }

    // ---------------------------------------------------------
    // REGISTRAR USUARIO
    // ---------------------------------------------------------
    fun registrarUsuario(usuario: User) {
        viewModelScope.launch {
            val entity = UserEntity(
                id = usuario.id,
                nombre = usuario.nombre,
                email = usuario.email,
                tipo = usuario.tipo,
                puntos = usuario.puntos
            )
            repo.insertUser(entity)
        }
    }

    // ---------------------------------------------------------
    // LOGIN
    // ---------------------------------------------------------
    fun login(email: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val u = repo.findUserByEmail(email)
            if (u != null) {
                usuarioActual.value = User(
                    id = u.id,
                    nombre = u.nombre,
                    email = u.email,
                    tipo = u.tipo,
                    puntos = u.puntos
                )
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    // ---------------------------------------------------------
    // PUBLICAR MATERIAL (PERSISTENTE)
    // ---------------------------------------------------------
    fun agregarPublicacion(post: RecyclePost, onComplete: () -> Unit = {}) {
        viewModelScope.launch {

            val entity = PostEntity(
                id = post.id,
                userId = post.userId,
                material = post.material,
                cantidad = post.cantidad,
                estado = post.estado
            )

            repo.insertPost(entity)

            // Sumar puntos al usuario que publica
            usuarioActual.value?.let { u ->
                val nuevosPuntos = u.puntos + 10
                u.puntos = nuevosPuntos

                repo.insertUser(
                    UserEntity(
                        id = u.id,
                        nombre = u.nombre,
                        email = u.email,
                        tipo = u.tipo,
                        puntos = nuevosPuntos
                    )
                )
            }

            onComplete()
        }
    }

    // ---------------------------------------------------------
    // ACTUALIZAR ESTADO (aceptar / rechazar)
    // ---------------------------------------------------------
    fun actualizarEstado(id: String, nuevoEstado: String, onComplete: (() -> Unit)? = null) {
        viewModelScope.launch {

            val post = repo.findPostById(id)

            if (post != null) {

                val actualizado = post.copy(estado = nuevoEstado)
                repo.updatePost(actualizado)

                // Si es aceptado, ganar puntos (solo reciclador)
                if (nuevoEstado == "aceptado") {
                    usuarioActual.value?.let { u ->
                        if (u.tipo == "reciclador") {

                            val nuevosPuntos = u.puntos + 20
                            u.puntos = nuevosPuntos

                            repo.insertUser(
                                UserEntity(
                                    id = u.id,
                                    nombre = u.nombre,
                                    email = u.email,
                                    tipo = u.tipo,
                                    puntos = nuevosPuntos
                                )
                            )
                        }
                    }
                }

                onComplete?.invoke()
            }
        }
    }
}
