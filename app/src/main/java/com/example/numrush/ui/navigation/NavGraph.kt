package com.example.numrush.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.numrush.ui.GameScreen
import com.example.numrush.ui.MainScreen

@Composable
fun AppNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Routes.MAIN
    ) {

        composable(Routes.MAIN) {
            MainScreen(navController)
        }

        composable(Routes.GAME) {
            GameScreen()
        }
    }
}