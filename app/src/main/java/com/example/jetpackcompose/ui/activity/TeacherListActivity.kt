package com.example.jetpackcompose.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackcompose.R
import com.example.jetpackcompose.component.ShowLog
import com.example.jetpackcompose.component.ToolBar
import com.example.jetpackcompose.model.UserDetail
import com.example.jetpackcompose.ui.theme.JetpackComposeTheme
import com.example.jetpackcompose.viewmodel.MovieListVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeacherListActivity : ComponentActivity() {

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMovieApp{
                ToolBar(getContext(),"Teachers List")
                val viewModel = hiltViewModel<MovieListVM>()
                val coroutineScope = rememberCoroutineScope()
                LaunchedEffect(Unit) {
                   viewModel.getTeachersData()
                  /*  coroutineScope.launch {
                        viewModel.getTeachersData()
                    }*/
               }
                if (viewModel.mLoder.value) ProgressBar()
                UsersData(viewModel)
            }
        }
    }
}

@Composable
fun UsersData(viewModel: MovieListVM) {
    val list = remember {
        viewModel.userDetail
    }
    LazyColumn{
        items(list.size){
            ShowLog(list.size.toString())
            val data = list[it]
            Data(data)
        }
    }

}

@Composable
fun Data(data: UserDetail) {
    val context = LocalContext.current
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable{
            onClickd(data.name, context)
        },
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        elevation = 5.dp) {
        Row(modifier = Modifier.fillMaxWidth()) {
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
    }
}

fun onClickd(clickState: String, context: Context) {
    Toast.makeText(context,clickState, Toast.LENGTH_SHORT).show()
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
        ToolBar(getContext(),"Movie App")
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
