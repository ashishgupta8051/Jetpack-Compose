package com.example.jetpackcompose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.model.Users
import com.example.jetpackcompose.network.Repository
import com.example.jetpackcompose.network.TypeOfApi
import com.example.jetpackcompose.util.ApiProcess
import com.example.jetpackcompose.util.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UsersVM @Inject constructor(private val repository: Repository) : ViewModel(){
    var onlineUsers = MutableStateFlow<List<Users>>(emptyList())
    var offlineUsers = MutableStateFlow<List<Users>>(emptyList())
    var getUserDataOnline = onlineUsers.asStateFlow()
    var getUserDataOffline = offlineUsers.asStateFlow()
    var mLoder = mutableStateOf(true)
    var id = mutableStateOf("0")
    var userDetails : Users = Users(0,"","", emptyList(), emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllUsers().distinctUntilChanged().collect{
                    list->
                offlineUsers.value = list
            }
        }

        repository.hitApi(type = TypeOfApi.TEACHERS_API, object :  ApiProcess<List<Users>>{
            override fun showLoader(loader: Boolean) {
                mLoder.value = loader
            }

            override suspend fun sendRequest(apiService: ApiService): Response<List<Users>> {
                return apiService.getUserData()
            }

            override fun success(response: Response<Any>) {
                onlineUsers.value = response.body() as MutableList<Users>
            }

            override fun failure(message: String) {

            }
        })

    }

    fun getUsersData(){
        repository.hitApi(type = TypeOfApi.TEACHERS_API,object :  ApiProcess<List<Users>>{
            override fun showLoader(loader: Boolean) {
                mLoder.value = loader
            }

            override suspend fun sendRequest(apiService: ApiService): Response<List<Users>> {
                return apiService.getUserData()
            }

            override fun success(response: Response<Any>) {
                onlineUsers.value = response.body() as MutableList<Users>
            }

            override fun failure(message: String) {

            }
        })
    }

    fun addUsers(users: Users) = viewModelScope.launch {
        repository.addUsers(users)
    }
    fun deleteUsers(users: Users) = viewModelScope.launch {
        repository.deleteUsers(users)
    }
    fun updateUsers(users: Users) = viewModelScope.launch {
        repository.updateUsers(users)
    }
    fun deleteAllUsers() = viewModelScope.launch {
        repository.deleteAllUsers()
    }
    fun getUserDetailsById(id: String) = viewModelScope.launch(Dispatchers.IO) {
        userDetails = repository.getUserDetailsById(id)
    }

}