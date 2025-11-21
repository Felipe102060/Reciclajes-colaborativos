package com.example.ecocolab.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ecocolab.navigation.AppScreen
import com.example.ecocolab.viewmodel.AppViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: AppViewModel = viewModel()
) {

    val usuario = viewModel.usuarioActual.value


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            // TÍTULO / BIENVENIDA
            Text(
                text = "Bienvenido, ${usuario?.nombre ?: "Usuario"}",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(30.dp))

            // BOTÓN 1 – Publicar material
            Button(
                onClick = {
                    navController.navigate(AppScreen.PostRecycle.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Publicar Material Reciclable")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // BOTÓN 2 – Ver solicitudes
            Button(
                onClick = {
                    navController.navigate(AppScreen.Requests.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Solicitudes de Recolección")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // BOTÓN 3 – Perfil
            Button(
                onClick = {
                    navController.navigate(AppScreen.Profile.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mi Perfil")
            }
        }
    }
}
