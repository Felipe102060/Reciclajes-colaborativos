package com.example.ecocolab.ui.screens.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ecocolab.data.model.User
import com.example.ecocolab.navigation.AppScreen
import com.example.ecocolab.viewmodel.AppViewModel

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: AppViewModel
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var tipoUsuario by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Crear cuenta",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            // NOMBRE
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // EMAIL
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // TIPO DE USUARIO (USUARIO / RECICLADOR)
            OutlinedTextField(
                value = tipoUsuario,
                onValueChange = { tipoUsuario = it },
                label = { Text("Tipo de usuario (Usuario / Reciclador)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // MENSAJE ERROR
            if (mensajeError.isNotBlank()) {
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // BOTÓN REGISTRAR
            Button(
                onClick = {

                    if (nombre.isBlank() || email.isBlank() || tipoUsuario.isBlank()) {
                        mensajeError = "Todos los campos son obligatorios"
                        return@Button
                    }

                    val emailRegex =
                        Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

                    if (!emailRegex.matches(email)) {
                        mensajeError = "Correo inválido"
                        return@Button
                    }

                    if (tipoUsuario.lowercase() !in listOf("usuario", "reciclador")) {
                        mensajeError = "Tipo debe ser Usuario o Reciclador"
                        return@Button
                    }

                    val nuevoUsuario = User(
                        id = System.currentTimeMillis().toString(),
                        nombre = nombre,
                        email = email,
                        tipo = tipoUsuario.lowercase(),
                        puntos = 0
                    )

                    // Guardar en Room
                    viewModel.registrarUsuario(nuevoUsuario)

                    // Navegar al Login, limpiando historial
                    navController.navigate(AppScreen.Login.route) {
                        popUpTo(AppScreen.Register.route) { inclusive = true }
                    }

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {
                navController.navigate(AppScreen.Login.route)
            }) {
                Text("Ya tengo una cuenta")
            }
        }
    }
}
