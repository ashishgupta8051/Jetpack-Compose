package com.example.jetpackcompose.ui.activity.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcompose.R
import com.example.jetpackcompose.ui.theme.JetpackComposeTheme

class MainActivity : ComponentActivity() {
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                Surface(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(), color = MaterialTheme.colors.background){
                    context = LocalContext.current
                    CardDesign(context)
                }
            }
        }
    }
}

@Composable
private fun CardDesign(context: Context) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(10.dp, 20.dp, 10.dp, 20.dp),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        elevation = 5.dp
    ) {
        val clickState = remember {
            mutableStateOf(false)
        }
        Column(modifier = Modifier.size(150.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CreateImageProfile()
            Divider(thickness = 1.dp)
            Column(modifier = Modifier.padding(5.dp)) {
                TextView()
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = {
                    clickState.value = !clickState.value
                }, modifier = Modifier.width(150.dp).padding(10.dp,0.dp,5.dp,0.dp),
                    elevation =  ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        disabledElevation = 0.dp,
                        hoveredElevation = 0.dp,
                        focusedElevation = 0.dp)
                ) {
                    Text(text = "Clicked", color = Color.White)
                }

                Button(onClick = {
                              context.startActivity(Intent(context,CalculatorActivity::class.java))
                }, modifier = Modifier.width(150.dp).padding(5.dp,0.dp,10.dp,0.dp),
                    elevation =  ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        disabledElevation = 0.dp,
                        hoveredElevation = 0.dp,
                        focusedElevation = 0.dp)
                ) {
                    Text(text = "Clicked", color = Color.White)
                }
            }

            onButtonClicked(clickState)


        }
    }
}

@Composable
fun onButtonClicked(clickState: MutableState<Boolean>) {
    if (clickState.value){
        ListData()
    }else{
        Box {
        }
    }
}


@Composable
private fun TextView() {
    Text(text = "Hello Ashish", color = Color.Red , fontSize = 28.sp)
    Text(text = "Android Developer", color = Color.Black , fontSize = 16.sp, modifier = Modifier.padding(0.dp,2.dp,0.dp,2.dp))
    Text(text = "Android Jetpack Compose Example", color = Color.Gray , fontSize = 16.sp, modifier = Modifier.padding(0.dp,0.dp,0.dp,0.dp))
}

@Composable
private fun CreateImageProfile(modifier: Modifier= Modifier) {
    Surface(
        modifier = modifier
            .size(150.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(1.dp, Color.LightGray),
        color = MaterialTheme.colors.surface.copy(alpha = 0.5f)
    ) {
        Image(
            painter = painterResource(id = R.mipmap.profile_image),
            contentDescription = "Profile Image",
            modifier = modifier.size(120.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun ListData(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(5.dp, 5.dp, 5.dp, 5.dp,)){
        Surface(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
            border = BorderStroke(1.dp,Color.Gray)
        ) {
            Portfolio(data = listOf<String>("Android 1","Android 2","Android 3","Android 4","Android 5","Android 6","Android 7"))
        }
    }
}

@Composable
fun Portfolio(data: List<String>) {
    LazyColumn{
        items(data){ item ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp, 10.dp, 0.dp), shape = RectangleShape, elevation = 5.dp) {
                Row(modifier = Modifier
                    .padding(8.dp)
                    .background(Color.White)) {
                    CreateImageProfile(modifier = Modifier.size(70.dp))
                    Column(modifier = Modifier
                        .padding(10.dp)
                        .align(alignment = Alignment.CenterVertically)) {
                        Text(text = item, fontWeight = FontWeight.Bold)
                        Text(text = "Android studio", fontWeight = FontWeight.Medium)

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeTheme {
        Surface(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(), color = MaterialTheme.colors.background){
            val context = LocalContext.current
            CardDesign(context)
        }
    }
}