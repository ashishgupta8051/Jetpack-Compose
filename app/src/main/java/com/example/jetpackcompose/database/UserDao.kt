package com.example.jetpackcompose.database

import android.service.autofill.UserData
import androidx.room.*
import com.example.jetpackcompose.model.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserData(users: Users)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserData(users: Users)

    @Delete
    suspend fun deleteUserData(users: Users)

    @Query("SELECT * from users_table")
    fun getUserList(): Flow<List<Users>>

    @Query("SELECT * from users_table where id = :id")
    suspend fun getUserDetailsById(id: String): Users

    @Query("DELETE from users_table")
    suspend fun deleteAllUserList()






}