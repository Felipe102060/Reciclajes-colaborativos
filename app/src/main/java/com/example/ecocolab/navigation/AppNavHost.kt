package com.example.ecocolab.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ecocolab.ui.screens.register.RegisterScreen
import com.example.ecocolab.ui.screens.home.HomeScreen
import com.example.ecocolab.ui.screens.login.LoginScreen
import com.example.ecocolab.ui.screens.post.PostRecycleScreen
import com.example.ecocolab.ui.screens.profile.ProfileScreen
import com.example.ecocolab.ui.screens.requests.RequestsScreen
import com.example.ecocolab.viewmodel.AppViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: AppViewModel
) {

    NavHost(
        navController = navController,
        startDestination = AppScreen.Login.route
    ) {

        composable(AppScreen.Login.route) {
            LoginScreen(navController, viewModel)
        }

        composable(AppScreen.Register.route) {
            RegisterScreen(navController, viewModel)
        }

        composable(AppScreen.Home.route) {
            HomeScreen(navController, viewModel)
        }

        composable(AppScreen.PostRecycle.route) {
            PostRecycleScreen(navController, viewModel)
        }

        composable(AppScreen.Requests.route) {
            RequestsScreen(navController, viewModel)
        }

        composable(AppScreen.Profile.route) {
            ProfileScreen(navController, viewModel)
        }
    }
}
