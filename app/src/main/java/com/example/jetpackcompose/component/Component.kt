package com.example.jetpackcompose.component


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Money
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    Card(modifier = modifier.padding(all = 4.dp).clickable{onClick.invoke()}.then(Modifier.size(40.dp)),
        shape = CircleShape,
        backgroundColor = backGroundColor,
        elevation = elevation,) {
        Icon(imageVector = imageVector, contentDescription = "Image", tint = tint)
    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ToolBar(context: Context, activityName: String) {
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        backgroundColor = colorResource(com.example.jetpackcompose.R.color.purple_500)
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            Icon(modifier = Modifier
                .padding(start = 12.dp)
                .size(26.dp)
                .clickable {
                    (context as Activity).finish()
                }, imageVector = Icons.Default.ArrowBack, contentDescription = "", tint = Color.White)
            Text(modifier = Modifier.padding(start = 12.dp), text = activityName, style = TextStyle(color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold))
        }
    }
}


fun ShowLog(message: String){
    Log.e("DATA",message)
}