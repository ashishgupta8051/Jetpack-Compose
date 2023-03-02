package com.example.jetpackcompose.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetpackcompose.model.UserDetail
import com.example.jetpackcompose.network.Repository
import com.example.jetpackcompose.network.TypeOfApi
import com.example.jetpackcompose.util.ApiProcess
import com.example.jetpackcompose.util.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieListVM @Inject constructor(private val repository: Repository) : ViewModel(){
    var userDetail = mutableStateListOf<UserDetail>()
    var mLoder = mutableStateOf(true)

    fun getTeachersData(){
        repository.hitApi(type = TypeOfApi.TEACHERS_API,object :  ApiProcess<ArrayList<UserDetail>>{
            override fun showLoader(loader: Boolean) {
                mLoder.value = loader
            }

            override suspend fun sendRequest(apiService: ApiService): Response<ArrayList<UserDetail>> {
                return apiService.getUserData()
            }

            override fun success(response: Response<Any>) {
                userDetail.addAll(response.body() as MutableList<UserDetail>)
            }

            override fun failure(message: String) {
            }
        })
    }


}