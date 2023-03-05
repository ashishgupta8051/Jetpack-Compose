package com.example.jetpackcompose.model.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken

//@ProvidedTypeConverter
class QualificationConverters {

    @TypeConverter
    fun fromQuaString(value: String?): List<String?>? {
        val listType = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromQuaList(list: List<String?>?): String? {
        return Gson().toJson(list)
    }
}