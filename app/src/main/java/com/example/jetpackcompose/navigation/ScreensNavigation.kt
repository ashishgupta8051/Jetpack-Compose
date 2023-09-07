package com.example.jetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcompose.ui.screen.UserDetailsScreen
import com.example.jetpackcompose.ui.screen.UserListScreen

@Composable
fun ScreensNavigation (){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreensName.UsersScreen.name){

        composable(ScreensName.UsersScreen.name) {
            UserListScreen(navController)
        }

        composable(ScreensName.DetailsScreen.name) {
            UserDetailsScreen(navController)
        }
    }


}