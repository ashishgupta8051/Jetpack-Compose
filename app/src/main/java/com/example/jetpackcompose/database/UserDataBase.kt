package com.example.jetpackcompose.database

import androidx.room.Database
import com.example.jetpackcompose.model.UserDetail

@Database(entities = [UserDetail::class], version = 1, exportSchema = false)
object UserDataBase {

/*
    fun getUserDao(): UserDao
*/

}