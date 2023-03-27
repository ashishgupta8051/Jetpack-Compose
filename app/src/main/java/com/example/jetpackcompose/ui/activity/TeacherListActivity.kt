package com.example.jetpackcompose.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackcompose.R
import com.example.jetpackcompose.component.InputField
import com.example.jetpackcompose.component.ToolBar
import com.example.jetpackcompose.model.Users
import com.example.jetpackcompose.ui.theme.JetpackComposeTheme
import com.example.jetpackcompose.util.checkInternetConnection
import com.example.jetpackcompose.viewmodel.UsersVM
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.TextStyle

@AndroidEntryPoint
class TeacherListActivity : ComponentActivity() {

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMovieApp{
                val viewModel = hiltViewModel<UsersVM>()
                if (checkInternetConnection(getContext())){
                    ToolBar(getContext(),"Teacher List")
                }else{
                    ToolBar(getContext(),"Teacher List")
                }
                val coroutineScope = rememberCoroutineScope()
                LaunchedEffect(Unit) {
                    viewModel.getUsersData()
               }
                if (viewModel.mLoder.value) ProgressBar()
                UsersData(viewModel)
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun UsersData(viewModel: UsersVM) {
    val list = viewModel.users.collectAsState().value

    if (checkInternetConnection(getContext())){
        LazyColumn{
            items(list.size){
                viewModel.addUsers(list[it])
                Data(list[it],viewModel)
            }
        }
    }else{
        val userList = viewModel.users2.collectAsState().value
        LazyColumn{
            items(userList.size){
                Data(userList[it], viewModel)
            }
        }

    }

}

@Composable
fun Data(data: Users, viewModel: UsersVM) {
    val context = LocalContext.current
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable {
//            onClickd(data.name, context)
        },
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        elevation = 5.dp) {
        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
            if (checkInternetConnection(getContext())){
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
                        .align(Alignment.CenterVertically)) {
                        Text(text = "Id: ${data.id}", fontSize = 14.sp)
                        Text(text = "Name: ${data.name}",fontSize = 14.sp ,modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                        Text(maxLines = 1, text = "Language: ${data.qualification.toString().replace("[","").replace("]","")}",fontSize = 14.sp)
                    }
                }
            }else{
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

                Row(modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 5.dp, top = 4.dp)) {
                    Icon(modifier = Modifier
                        .padding(end = 6.dp, top = 3.dp)
                        .size(26.dp).clickable {
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
                    Text(text = "Name",fontSize = 15.sp ,modifier = Modifier.padding(top = 12.dp).align(Alignment.Start), color = colorResource(id = R.color.black))
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
                        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp)
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
fun MyMovieApp (content: @Composable () -> Unit){
    JetpackComposeTheme {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colors.background)) {
            Column() {
                content()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    MyMovieApp {
        if (checkInternetConnection(getContext())){
            ToolBar(getContext(),"Teacher List")
        }else{
            ToolBar(getContext(),"Teacher List")
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
private fun getContext(): Context{
    return LocalContext.current
}
