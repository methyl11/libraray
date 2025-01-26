package com.x3lnthpi.library

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.x3lnthpi.library.views.HomeScreen
import com.x3lnthpi.library.views.PrintScreen
import com.x3lnthpi.library.views.comonents.FullImageView

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable("HomeScreen") { HomeScreen(navController) }
        composable("PrintScreen") { PrintScreen(navController) }
        }
    }


