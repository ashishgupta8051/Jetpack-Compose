package com.example.jetpackcompose.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class Users(
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name ="name")
    var name: String,
    @ColumnInfo(name = "profileImage")
    var profileImage: String,
    @ColumnInfo(name = "qualification")
    var qualification: List<String>,
    @ColumnInfo(name = "subjects")
    var subjects: List<String>
)
