package com.example.jetpackcompose.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class Users(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name ="name")
    val name: String,
    @ColumnInfo(name = "profileImage")
    val profileImage: String,
    @ColumnInfo(name = "qualification")
    val qualification: List<String>,
    @ColumnInfo(name = "subjects")
    val subjects: List<String>
)
