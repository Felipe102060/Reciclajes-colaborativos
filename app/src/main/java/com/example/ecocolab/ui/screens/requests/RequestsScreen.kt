package com.example.ecocolab.ui.screens.requests

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ecocolab.viewmodel.AppViewModel
import com.example.ecocolab.data.model.RecyclePost

@Composable
fun RequestsScreen(
    navController: NavHostController,
    viewModel: AppViewModel
) {

    val usuario = viewModel.usuarioActual.value       // <- necesitamos saber si es reciclador
    val publicaciones = viewModel.listaPublicaciones.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        // ---------- BOTÓN VOLVER CORREGIDO ----------
        Button(
            onClick = {
                navController.navigate("home") {
                    popUpTo("home") { inclusive = false }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("⬅ Volver al Menú")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Solicitudes de Material",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (publicaciones.isEmpty()) {
            Text("No hay publicaciones aún.")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(publicaciones) { post ->
                    PublicacionItem(
                        post = post,
                        esReciclador = usuario?.tipo == "reciclador",
                        onAceptar = {
                            viewModel.actualizarEstado(post.id, "aceptado")
                        },
                        onRechazar = {
                            viewModel.actualizarEstado(post.id, "rechazado")
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun PublicacionItem(
    post: RecyclePost,
    esReciclador: Boolean,
    onAceptar: () -> Unit,
    onRechazar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text("Material: ${post.material}", style = MaterialTheme.typography.titleMedium)
            Text("Cantidad: ${post.cantidad}")
            Text("Estado: ${post.estado}")

            Spacer(modifier = Modifier.height(10.dp))

            // ------- SOLO RECICLADOR VE ESTOS BOTONES -------
            if (post.estado == "pendiente" && esReciclador) {

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                    Button(
                        onClick = onAceptar,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Aceptar")
                    }

                    OutlinedButton(
                        onClick = onRechazar,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Rechazar")
                    }
                }
            }
        }
    }
}

