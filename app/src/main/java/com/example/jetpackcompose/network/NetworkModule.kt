package com.example.jetpackcompose.network

import android.content.Context
import androidx.room.Room
import com.example.jetpackcompose.BuildConfig
import com.example.jetpackcompose.database.UserDao
import com.example.jetpackcompose.database.UserDataBase
import com.example.jetpackcompose.util.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @MovieQualifiers
    @Singleton
    @Provides
    fun provideMovieObj(gsonConverterFactory: GsonConverterFactory): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient =  OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).connectTimeout(30, TimeUnit.SECONDS).addInterceptor(loggingInterceptor).build()
        return Retrofit.Builder().baseUrl(BuildConfig.MOVIE_URL).client(okHttpClient).addConverterFactory(gsonConverterFactory).build().create(ApiService::class.java)
    }

    @TeachersQualifiers
    @Singleton
    @Provides
    fun provideTeachersObj(gsonConverterFactory: GsonConverterFactory): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient =  OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).connectTimeout(30, TimeUnit.SECONDS).addInterceptor(loggingInterceptor).build()
        return Retrofit.Builder().baseUrl(BuildConfig.TEACHER_URL).client(okHttpClient).addConverterFactory(gsonConverterFactory).build().create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun gsonConverter(): GsonConverterFactory = GsonConverterFactory.create()


    @Singleton
    @Provides
    fun getUserDao(userDataBase: UserDataBase): UserDao = userDataBase.getUserDao()


    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): UserDataBase = Room.databaseBuilder(
        context,
        UserDataBase::class.java,
        "users_db").build()

}