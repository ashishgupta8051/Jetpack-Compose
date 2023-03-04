package com.example.jetpackcompose.util

import com.example.jetpackcompose.model.Users
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUserData():Response<List<Users>>
}