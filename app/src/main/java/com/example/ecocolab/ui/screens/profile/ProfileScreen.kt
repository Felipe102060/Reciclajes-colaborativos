package com.example.ecocolab.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ecocolab.navigation.AppScreen
import com.example.ecocolab.viewmodel.AppViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: AppViewModel
) {

    val usuario by viewModel.usuarioActual

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Mi Perfil",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        PerfilDato("Nombre", usuario?.nombre ?: "-")
        PerfilDato("Correo", usuario?.email ?: "-")
        PerfilDato("Tipo", usuario?.tipo ?: "-")
        PerfilDato("Puntos ecológicos", usuario?.puntos?.toString() ?: "0")

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { navController.navigate(AppScreen.Home.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al menú")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                // logout: limpiar usuarioActual y regresar al Login limpiando backstack
                viewModel.usuarioActual.value = null
                navController.navigate(AppScreen.Login.route) {
                    popUpTo(AppScreen.Login.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}

@Composable
fun PerfilDato(label: String, valor: String) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(label, style = MaterialTheme.typography.labelLarge)
        Text(valor, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
    }
}
