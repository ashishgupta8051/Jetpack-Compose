package com.example.jetpackcompose.database

import androidx.room.*
import com.example.jetpackcompose.model.UserDetail

interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserData(userDetail: UserDetail)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserData(userDetail: UserDetail)

    @Delete
    fun deleteUserData(userDetail: UserDetail)

    @Query("SELECT * from user_detail")
    fun getUserList(): List<UserDetail>

    @Query("SELECT * from user_detail WHERE id =:1d")
    fun getUserDetailsById(id: String): UserDetail

    @Query("DELETE from user_detail")
    fun deleteAllUserList()






}