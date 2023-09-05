package com.example.jetpackcompose.ui.activity

import android.app.Activity
import android.app.Application
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}