package com.example.ecocolab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.ecocolab.navigation.AppNavHost
import com.example.ecocolab.ui.theme.EcoColabTheme
import com.example.ecocolab.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoColabTheme {
                val navController = rememberNavController()
                AppNavHost(navController, viewModel)
            }
        }
    }
}
