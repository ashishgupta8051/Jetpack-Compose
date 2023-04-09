package com.example.jetpackcompose.component


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Money
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackcompose.R
import com.example.jetpackcompose.model.Users
import com.example.jetpackcompose.ui.screen.ComposableLifecycle
import com.example.jetpackcompose.util.checkInternetConnection
import com.example.jetpackcompose.viewmodel.UsersVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine:Boolean,
    keyBoardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default){

    OutlinedTextField(
        modifier = modifier,
        value = valueState.value,
        onValueChange = {valueState.value = it},
        label = { Text(text = labelId)},
        leadingIcon = { Icon(imageVector = Icons.Rounded.Money, contentDescription = "Money Icon")},
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 16.sp, color = MaterialTheme.colors.onBackground),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType, imeAction = imeAction),
        keyboardActions = onAction)

}

@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color.Black.copy(alpha = 0.8f),
    backGroundColor: Color = MaterialTheme.colors.background,
    elevation: Dp = 4.dp){
    Card(modifier = modifier
        .padding(all = 4.dp)
        .clickable { onClick.invoke() }
        .then(Modifier.size(40.dp)),
        shape = CircleShape,
        backgroundColor = backGroundColor,
        elevation = elevation,) {
        Icon(imageVector = imageVector, contentDescription = "Image", tint = tint)
    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ToolBar(context: Context, activityName: String, viewModel: UsersVM = hiltViewModel(), navController: NavHostController = NavHostController(LocalContext.current)) {
    Scaffold(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
        backgroundColor = colorResource(R.color.purple_500)
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Row(modifier = Modifier
                .fillMaxHeight(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                if (activityName == "User Details"){
                    Icon(modifier = Modifier
                        .padding(start = 12.dp)
                        .size(26.dp)
                        .clickable {
                            navController.popBackStack()
                        }, imageVector = Icons.Default.ArrowBack, contentDescription = "", tint = Color.White)
                    Text(modifier = Modifier.padding(start = 12.dp), text = activityName, style = TextStyle(color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold))
                }else{
                    Text(modifier = Modifier.padding(start = 12.dp), text = activityName, style = TextStyle(color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold))
                }
            }

            if (!checkInternetConnection(context) && activityName != "User Details"){
                val scope = rememberCoroutineScope()
                Row(modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 8.dp)) {
                    Icon(modifier = Modifier
                        .size(26.dp)
                        .clickable {
                            if (viewModel.users2.value.isEmpty()) {
                                Toast
                                    .makeText(
                                        context,
                                        "All User is already deleted",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            } else {
                                viewModel.mLoder.value = true
                                scope.launch {
                                    delay(500L)
                                    viewModel.deleteAllUsers()
                                    viewModel.mLoder.value = false
                                    Toast.makeText(context,"All User is deleted",Toast.LENGTH_SHORT).show()
                                }

                            }
                        }
                        , imageVector = Icons.Default.Delete, contentDescription = "", tint = Color.Red)
                }
            }
        }
    }
}

@Composable
fun ProgressBar() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun CreateCustomDialog(viewModel: UsersVM, data: Users, onDismissRequest: (Boolean) -> Unit) {
    var name = remember { mutableStateOf(data.name) }
    Dialog(onDismissRequest = { onDismissRequest(false) }, properties = DialogProperties(dismissOnClickOutside = true)) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Box(contentAlignment = Alignment.Center){
                Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Update Name",fontSize = 18.sp ,modifier = Modifier.padding(top = 8.dp), color = colorResource(id = R.color.black))
                    Text(text = "Name",fontSize = 15.sp ,modifier = Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.Start), color = colorResource(id = R.color.black)
                    )
                    TextField(modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .height(48.dp)
                        .border(
                            BorderStroke(
                                width = 1.dp,
                                color = colorResource(id = if (name.value.isEmpty()) android.R.color.holo_green_light else android.R.color.black)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ),
                        value = name.value,
                        onValueChange = {name.value = it},
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        maxLines = 1,
                        textStyle = TextStyle(fontSize = 14.sp)
                    )

                    Row(modifier = Modifier.padding(top = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Button(
                            onClick = {
                                onDismissRequest(false)
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .height(45.dp)
                                .padding(top = 10.dp)
                        ) {
                            Text(text = "Cancel")
                        }

                        Spacer(modifier = Modifier.padding(start = 4.dp, end = 4.dp))

                        Button(
                            onClick = {
                                var users = Users(data.id,name.value,data.profileImage,data.qualification, data.subjects)
                                viewModel.updateUsers(users)
                                onDismissRequest(false)
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .height(45.dp)
                                .padding(top = 10.dp)
                        ) {
                            Text(text = "Update User")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UsersListScreen(data: Users, viewModel: UsersVM, context: Context) {
    var dialogOpen = remember{
        mutableStateOf(false)
    }
    Row() {
        Image(
            modifier = Modifier
                .size(80.dp)
                .padding(top = 5.dp, bottom = 5.dp, start = 5.dp),
            painter = rememberAsyncImagePainter(data.profileImage, placeholder = painterResource(id = R.mipmap.profile_image), error = painterResource(id = R.mipmap.profile_image)),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop)

        Column(modifier = Modifier
            .padding(start = 5.dp)
            .width(170.dp)
            .align(Alignment.CenterVertically)) {
            Text(text = "Id: ${data.id}", fontSize = 14.sp)
            Text(text = "Name: ${data.name}",fontSize = 14.sp ,modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
            Text(maxLines = 1, text = "Language: ${data.qualification.toString().replace("[","").replace("]","")}",fontSize = 14.sp)
        }
    }

    if (!checkInternetConnection(context)){
        Row(modifier = Modifier
            .wrapContentWidth()
            .padding(end = 5.dp, top = 4.dp)) {
            Icon(modifier = Modifier
                .padding(end = 6.dp, top = 3.dp)
                .size(26.dp)
                .clickable {
                    dialogOpen.value = true
                }
                , imageVector = Icons.Default.Edit, contentDescription = "", tint = Color.Black)
            Icon(modifier = Modifier
                .padding(top = 3.dp)
                .size(26.dp)
                .clickable {
                    viewModel.deleteUsers(data)
                    Toast
                        .makeText(context, "User Deleted", Toast.LENGTH_SHORT)
                        .show()

                }, imageVector = Icons.Default.Delete, contentDescription = "", tint = Color.Red)
        }

        if (dialogOpen.value){
            CreateCustomDialog(viewModel, data, onDismissRequest = {
                dialogOpen.value = it
            })
        }else{
//                    viewModel.dialogOpen.value = false
        }
    }
}

@Composable
fun LifecycleEvents(lifecycle: MutableState<String>){
    ComposableLifecycle { _, event ->
        when(event){
            Lifecycle.Event.ON_CREATE -> {
                Log.e("Lifecycle","ON_CREATE")
                lifecycle.value = com.example.jetpackcompose.util.LifecycleEvents.ON_CREATE.name
            }
            Lifecycle.Event.ON_START -> {
                Log.e("Lifecycle","ON_START")
                lifecycle.value = com.example.jetpackcompose.util.LifecycleEvents.ON_START.name
            }
            Lifecycle.Event.ON_RESUME -> {
                Log.e("Lifecycle","ON_RESUME")
                lifecycle.value = com.example.jetpackcompose.util.LifecycleEvents.ON_RESUME.name
            }
            Lifecycle.Event.ON_PAUSE -> {
                Log.e("Lifecycle","ON_PAUSE")
                lifecycle.value = com.example.jetpackcompose.util.LifecycleEvents.ON_PAUSE.name
            }
            Lifecycle.Event.ON_STOP -> {
                Log.e("Lifecycle","ON_STOP")
                lifecycle.value = com.example.jetpackcompose.util.LifecycleEvents.ON_STOP.name
            }
            Lifecycle.Event.ON_DESTROY -> {
                Log.e("Lifecycle","ON_DESTROY")
                lifecycle.value = com.example.jetpackcompose.util.LifecycleEvents.ON_DESTROY.name
            }
            Lifecycle.Event.ON_ANY -> {
                Log.e("Lifecycle-->", "ON_ANY")
                lifecycle.value = com.example.jetpackcompose.util.LifecycleEvents.ON_ANY.name
            }
            else -> {

            }
        }
    }
}




