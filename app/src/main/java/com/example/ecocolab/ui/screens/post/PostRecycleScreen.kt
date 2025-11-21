package com.example.ecocolab.ui.screens.post

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ecocolab.data.model.RecyclePost
import com.example.ecocolab.navigation.AppScreen
import com.example.ecocolab.viewmodel.AppViewModel

@Composable
fun PostRecycleScreen(
    navController: NavHostController,
    viewModel: AppViewModel
) {

    var material by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    val usuario = viewModel.usuarioActual.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Publicar Material",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = material,
            onValueChange = { material = it },
            label = { Text("Tipo de material") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad (kg / unidades)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (material.isBlank() || cantidad.isBlank()) {
                    mensaje = "Completa todos los campos"
                } else {

                    val newPost = RecyclePost(
                        id = System.currentTimeMillis().toString(),
                        userId = usuario?.id ?: "",
                        material = material,
                        cantidad = cantidad,
                        estado = "pendiente"
                    )

                    // 👇 CORRECTO: publicar usando callback
                    viewModel.agregarPublicacion(newPost) {
                        navController.navigate(AppScreen.Home.route)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Publicar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (mensaje.isNotEmpty()) {
            Text(text = mensaje, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = {
                navController.popBackStack()  // 👈 POR FIN FUNCIONA
            }
        ) {
            Text("Volver al Menú")
        }
    }
}
