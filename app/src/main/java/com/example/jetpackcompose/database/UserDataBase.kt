package com.example.jetpackcompose.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.jetpackcompose.model.converters.LanguageConverters
import com.example.jetpackcompose.model.converters.QualificationConverters
import com.example.jetpackcompose.model.Users

@Database(entities = [Users::class], version = 1, exportSchema = false)
@TypeConverters(LanguageConverters::class, QualificationConverters::class)
abstract class UserDataBase : RoomDatabase(){
    abstract fun getUserDao(): UserDao

}