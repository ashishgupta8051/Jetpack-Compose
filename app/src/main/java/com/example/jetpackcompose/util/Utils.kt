package com.example.jetpackcompose.util

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

fun ShowLog(message: String){
    Log.e("DATA",message)
}


fun checkInternetConnection(context: Context): Boolean{
    if (context != null) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val netInfo = connectivityManager.activeNetworkInfo
            if (netInfo != null && netInfo.isConnected) {
                return true
            }
        }
    }
    return false
}