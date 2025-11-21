package com.example.ecocolab.navigation

sealed class AppScreen(val route: String) {
    object Login : AppScreen("login")
    object Register : AppScreen("register")
    object Home : AppScreen("home")

    object PostRecycle : AppScreen("postRecycle")
    object Requests : AppScreen("requests")
    object Profile : AppScreen("profile")
}
