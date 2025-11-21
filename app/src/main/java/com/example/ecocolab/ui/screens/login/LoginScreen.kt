package com.example.ecocolab.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ecocolab.navigation.AppScreen
import com.example.ecocolab.viewmodel.AppViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: AppViewModel
) {

    var email by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "EcoColab",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Campo Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Botón Login
            Button(
                onClick = {
                    if (email.isBlank()) {
                        mensajeError = "Ingresa un correo"
                        return@Button
                    }

                    viewModel.login(email) { ok ->
                        if (ok) {
                            mensajeError = ""
                            navController.navigate(AppScreen.Home.route) {
                                popUpTo(AppScreen.Login.route) { inclusive = true }
                            }
                        } else {
                            mensajeError = "El usuario no existe"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sesión")
            }

            // Error
            if (mensajeError.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            TextButton(
                onClick = {
                    navController.navigate(AppScreen.Register.route)
                }
            ) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }
        }
    }
}
