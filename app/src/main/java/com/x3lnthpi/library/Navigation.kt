package com.x3lnthpi.library

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.x3lnthpi.library.views.AuthScreen
import com.x3lnthpi.library.views.HomeScreen
import com.x3lnthpi.library.views.PrintScreen
import com.x3lnthpi.library.views.comonents.FullImageView

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    //val authViewModel: AuthViewModel = viewModel() // Get ViewModel
    val savedImageViewModel : SavedImageViewModel = viewModel()
    NavHost(navController = navController, startDestination = "AuthScreen") {
        composable("HomeScreen") { HomeScreen(navController) }
        composable("PrintScreen") { PrintScreen(navController) }
        composable("AuthScreen"){ AuthScreen(navController)  }
        composable("SavedImagesScreen"){ SavedImagesScreen(navController, savedImageViewModel) }
        composable("VideoScreen"){ VideoPlayerScreen() }
        composable("TVScreen"){ TVScreen() }
        }
    }


