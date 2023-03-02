package com.example.jetpackcompose.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_detail")
data class UserDetail(
    @PrimaryKey
    val id: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("profile_image")
    val profileImage: String,
    @ColumnInfo("qualification")
    val qualification: List<String>,
    @ColumnInfo("subjects")
    val subjects: List<String>
)