package com.example.jetpackcompose.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.example.jetpackcompose.component.LifecycleEvents
import com.example.jetpackcompose.component.ProgressBar
import com.example.jetpackcompose.component.ToolBar
import com.example.jetpackcompose.component.UsersListScreen
import com.example.jetpackcompose.model.Users
import com.example.jetpackcompose.navigation.ScreensName
import com.example.jetpackcompose.ui.activity.MainActivity
import com.example.jetpackcompose.util.LifecycleEvents
import com.example.jetpackcompose.util.checkInternetConnection
import com.example.jetpackcompose.viewmodel.UsersVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun UserListScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colors.background)
    ) {
        Column {
            val lifecycle = remember { mutableStateOf("") }
            val viewModel = hiltViewModel<UsersVM>()
            val context = LocalContext.current
            val doubleClick = remember { mutableStateOf(false) }
            val coroutineScope = rememberCoroutineScope()

            /*
                        ComposableLifecycle { _, event ->
                            when(event){
                                Lifecycle.Event.ON_CREATE -> {
                                    Log.e("Lifecycle","ON_CREATE")
                                    lifecycle.value = LifecycleEvents.ON_CREATE.name
                                }
                                Lifecycle.Event.ON_START -> {
                                    Log.e("Lifecycle","ON_START")
                                    lifecycle.value = LifecycleEvents.ON_START.name
                                }
                                Lifecycle.Event.ON_RESUME -> {
                                    Log.e("Lifecycle","ON_RESUME")
                                    lifecycle.value = LifecycleEvents.ON_RESUME.name
                                }
                                Lifecycle.Event.ON_PAUSE -> {
                                    Log.e("Lifecycle","ON_PAUSE")
                                    lifecycle.value = LifecycleEvents.ON_PAUSE.name
                                }
                                Lifecycle.Event.ON_STOP -> {
                                    Log.e("Lifecycle","ON_STOP")
                                    lifecycle.value = LifecycleEvents.ON_STOP.name
                                }
                                Lifecycle.Event.ON_DESTROY -> {
                                    Log.e("Lifecycle","ON_DESTROY")
                                    lifecycle.value = LifecycleEvents.ON_DESTROY.name
                                }
                                Lifecycle.Event.ON_ANY -> {
                                    Log.e("Lifecycle-->", "ON_ANY")
                                    lifecycle.value = LifecycleEvents.ON_ANY.name
                                }
                                else -> {

                                }
                            }
                        }
            */

            LifecycleEvents(lifecycle)

            when (lifecycle.value) {
                LifecycleEvents.ON_CREATE.name -> {
                }

                LifecycleEvents.ON_START.name -> {
                }

                LifecycleEvents.ON_RESUME.name -> {

                    if (checkInternetConnection(getContext())) {
                        ToolBar(getContext(), "User List")
                    } else {
                        ToolBar(getContext(), "User List", viewModel)
                    }
                    LaunchedEffect(Unit) {
                        //  viewModel.getUsersData()
                    }
                    if (viewModel.mLoder.value) ProgressBar()
                    UsersData(viewModel, navController)
                }

                LifecycleEvents.ON_PAUSE.name -> {
                }

                LifecycleEvents.ON_STOP.name -> {
                }

                LifecycleEvents.ON_DESTROY.name -> {
                }

                LifecycleEvents.ON_ANY.name -> {
                }

                else -> {

                }
            }

            BackHandler {
                if (doubleClick.value) {
                    (context as MainActivity).finish()
                } else {
                    Toast.makeText(context, "Please click BACK again", Toast.LENGTH_SHORT).show()
                    doubleClick.value = true
                    coroutineScope.launch {
                        delay(2000L)
                        doubleClick.value = false
                    }
                }
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun UsersData(viewModel: UsersVM, navController: NavHostController) {
    if (checkInternetConnection(getContext())) {
        //    val list = viewModel.users.collectAsState().value
        val list = viewModel.getUserDataOnline.value
        LazyColumn {
            items(list.size) {
                viewModel.addUsers(list[it])
                Data(list[it], viewModel, navController)
            }
        }
    } else {
        val offline_list = viewModel.offlineUsers.collectAsState().value
//        val offline_list = viewModel.getUserDataOffline.value
        LazyColumn {
            items(offline_list.size) {
                Data(offline_list[it], viewModel, navController)
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Data(data: Users, viewModel: UsersVM, navController: NavHostController) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        elevation = 5.dp
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    if (!checkInternetConnection(context)) {
                        val id: String = data.id.toString()
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "user_id",
                            id
                        )
                        navController.navigate(ScreensName.DetailsScreen.name)
                    }
                }, onLongClick = {
                    Toast
                        .makeText(context, "Long Click", Toast.LENGTH_SHORT)
                        .show()
                }, onDoubleClick = {
                    Toast
                        .makeText(context, "Double Click", Toast.LENGTH_SHORT)
                        .show()
                }), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            UsersListScreen(data, viewModel, context)
        }
    }
}


@Composable
private fun getContext(): Context {
    return LocalContext.current
}

@Composable
fun ComposableLifecycle(
    lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit
) {
    DisposableEffect(lifeCycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            onEvent(source, event)
        }
        lifeCycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

