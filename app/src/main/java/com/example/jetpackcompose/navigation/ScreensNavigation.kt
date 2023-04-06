package com.example.jetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcompose.ui.screen.SplashScreen
import com.example.jetpackcompose.ui.screen.UserListScreen

@Composable
fun ScreensNavigation (){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreensName.SplashScreen.name){
        composable(ScreensName.SplashScreen.name) {
            SplashScreen(navController)
        }

        composable(ScreensName.UsersScreen.name) {
            UserListScreen(navController)
        }
    }


}