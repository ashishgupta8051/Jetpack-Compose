package com.example.jetpackcompose.network

import android.util.Log
import com.example.jetpackcompose.util.ApiProcess
import com.example.jetpackcompose.util.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(@MovieQualifiers private val apiService1: ApiService,@TeachersQualifiers private val apiService2: ApiService) {

    fun <T> hitApi(type: TypeOfApi,request: ApiProcess<ArrayList<T>>) {

        val hitApi: Flow<Response<Any>> = flow {
            val response = request.sendRequest(when(type){
                TypeOfApi.MOVIES_API -> apiService1
                TypeOfApi.TEACHERS_API -> apiService2
            }) as Response<Any>
            emit(response)
        }.flowOn(Dispatchers.IO)

        CoroutineScope(Dispatchers.Main).launch {
            hitApi.catch {
                Log.d("data-->","${it.cause}")
                Log.d("data-->","${it.message}")
                Log.d("data-->","${it.localizedMessage}")
                request.showLoader(false)
                it.message?.let { it1 -> request.failure(it1) }
            }.collect{response->
                request.showLoader(false)
                request.success(response)
            }
        }

    }}