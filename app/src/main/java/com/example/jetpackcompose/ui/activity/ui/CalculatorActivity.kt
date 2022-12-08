package com.example.jetpackcompose.ui.activity.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcompose.ui.activity.component.InputField
import com.example.jetpackcompose.ui.activity.component.RoundIconButton
import com.example.jetpackcompose.ui.theme.JetpackComposeTheme

class CalculatorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCalculatorApp {
                TopHeader()
                MainBody()
            }
        }
    }
}

@Composable
fun TopHeader(totalPerPerson:Double = 0.0){
    Surface(modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 10.dp)
        .fillMaxWidth()
        .height(150.dp)
        .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp)))
        , color = Color.Gray) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(text = "Total per Person", style = MaterialTheme.typography.h6)
            Text(text = "$$ totalPerPerson", style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
@OptIn(ExperimentalComposeUiApi::class)
private fun BillForm(modifier: Modifier = Modifier, onValChange: (String) -> Unit = {}) {
    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    val keyBoardControl = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 25.dp, end = 25.dp, bottom = 20.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.Gray)) {
        Column(
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            InputField(
                modifier = Modifier.fillMaxWidth().padding(all = 0.dp),
                valueState = totalBillState,
                labelId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions

                    onValChange(totalBillState.value.trim())

                    keyBoardControl?.hide()
                }
            )

            if (!validState){
                Row(modifier = Modifier.padding(top = 20.dp, start = 5.dp, end = 5.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {

                    Text(text = "Split", style = TextStyle(color = Color.Black, fontSize = 20.sp))

                    Spacer(modifier = Modifier.width(100.dp))

                    Row {
                        RoundIconButton(modifier = Modifier.padding(end = 20.dp), imageVector = Icons.Default.Remove, onClick = {

                        })

                        RoundIconButton(modifier = Modifier.padding(start = 20.dp), imageVector = Icons.Default.Add, onClick = {

                        })
                    }
                }
            }else{
                Box {

                }
            }
        }

    }
}


@Composable
fun MainBody(){
    BillForm(Modifier.padding(20.dp)){
        Log.d("VALUE: ", it)
    }
}


@Composable
fun MyCalculatorApp(content:  @Composable () -> Unit){
    JetpackComposeTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column {
                content()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    JetpackComposeTheme {
        MyCalculatorApp {
            TopHeader()
            MainBody()
        }
    }
}