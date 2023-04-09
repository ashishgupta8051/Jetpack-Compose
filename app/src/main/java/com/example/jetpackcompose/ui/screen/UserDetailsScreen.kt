package com.example.jetpackcompose.ui.screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackcompose.R
import com.example.jetpackcompose.component.LifecycleEvents
import com.example.jetpackcompose.component.ProgressBar
import com.example.jetpackcompose.component.ToolBar
import com.example.jetpackcompose.model.Users
import com.example.jetpackcompose.ui.activity.MainActivity
import com.example.jetpackcompose.util.LifecycleEvents
import com.example.jetpackcompose.util.checkInternetConnection
import com.example.jetpackcompose.viewmodel.UsersVM
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun UserDetailsScreen(navController: NavHostController = NavHostController(LocalContext.current)) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(MaterialTheme.colors.background)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {

            val lifecycle = remember { mutableStateOf("") }

            val viewmodel = hiltViewModel<UsersVM>()

            if (!checkInternetConnection(getContext())){
                ToolBar(getContext(),"User Details",viewmodel, navController)
            }
            val userId = navController.previousBackStackEntry?.savedStateHandle?.get<String>("user_id")
            if (userId != null){
                val data: Users?
                viewmodel.getUserDetailsById(userId)
                data = viewmodel.userDetails
                UserDetails(data,navController,viewmodel)
            }

            LifecycleEvents(lifecycle)

            when(lifecycle.value){
                LifecycleEvents.ON_CREATE.name -> {
                }
                LifecycleEvents.ON_START.name -> {
                }
                LifecycleEvents.ON_RESUME.name -> {
                }
                LifecycleEvents.ON_PAUSE.name -> {
                }
                LifecycleEvents.ON_STOP.name -> {
                }
                LifecycleEvents.ON_DESTROY.name-> {
                }
                LifecycleEvents.ON_ANY.name -> {
                }
                else -> {

                }
            }
        }
        
    }

    BackHandler {
        navController.popBackStack()
    }
}

@Composable
fun UserDetails(data: Users, navController: NavHostController, viewmodel: UsersVM) {
    Card(modifier = Modifier
        .size(150.dp)
        .padding(top = 20.dp),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        elevation = 5.dp) {

        Image(
            painter = rememberAsyncImagePainter(data.profileImage, placeholder = painterResource(id = R.mipmap.profile_image), error = painterResource(id = R.mipmap.profile_image)),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop)
    }

    Column(modifier = Modifier
        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        .fillMaxWidth()) {
        Text(text = "Id: ${data.id}", fontSize = 24.sp)
        Text(text = "Name: ${data.name}",fontSize = 24.sp ,modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
        Text(maxLines = 1, text = "Language: ${data.qualification.toString().replace("[","").replace("]","")}",fontSize = 24.sp)
    }
}


@Composable
private fun getContext(): Context {
    return LocalContext.current
}