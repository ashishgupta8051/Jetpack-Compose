package com.example.jetpackcompose.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.example.jetpackcompose.R
import com.example.jetpackcompose.navigation.ScreensName
import com.example.jetpackcompose.ui.activity.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun SplashScreen(navController: NavHostController = NavHostController(LocalContext.current)) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            val context = LocalContext.current
            Image(painter = painterResource(id = R.mipmap.ic_splash_screen), contentDescription ="splash image", modifier = Modifier.size(200.dp))

            LaunchedEffect(key1 = Unit) {
                delay(2000L)
                navController.popBackStack()
                navController.navigate(ScreensName.UsersScreen.name)
            }
        }
    }
}