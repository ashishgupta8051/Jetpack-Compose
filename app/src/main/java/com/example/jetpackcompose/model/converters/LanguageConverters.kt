package com.example.jetpackcompose.model.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken

//@ProvidedTypeConverter
class LanguageConverters {

    @TypeConverter
    fun fromLangString(value: String?): ArrayList<String?>? {
        val listType = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromLangList(list: ArrayList<String?>?): String? {
        return Gson().toJson(list)
    }
}