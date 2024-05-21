package com.ervilitasila.githubapp.network

import com.ervilitasila.githubapp.model.Repository
import com.ervilitasila.githubapp.model.User
import com.ervilitasila.githubapp.model.UserProfile
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://api.github.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface UserApiService {
    @GET("users")
    suspend fun getUsers() : List<User>

    @GET("users/{loginUser}")
    suspend fun getUser(@Path("loginUser") loginUser: String) : UserProfile

    @GET("users/{loginUser}/repos")
    suspend fun getRepositories(@Path("loginUser") loginUser: String) : List<Repository>
}

object UserApi {
    val retrofitService : UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
}

