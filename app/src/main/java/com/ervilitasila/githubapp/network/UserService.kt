package com.ervilitasila.githubapp.network

import com.ervilitasila.githubapp.model.Repository
import com.ervilitasila.githubapp.model.User
import com.ervilitasila.githubapp.model.UserProfile
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("users")
    suspend fun getUsers() : List<User>

    @GET("users/{loginUser}")
    suspend fun getUser(@Path("loginUser") loginUser: String) : UserProfile

    @GET("users/{loginUser}/repos")
    suspend fun getRepositories(@Path("loginUser") loginUser: String) : List<Repository>
}