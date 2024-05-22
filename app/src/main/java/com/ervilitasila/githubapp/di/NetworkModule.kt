package com.ervilitasila.githubapp.di

import com.ervilitasila.githubapp.network.UserService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val BASE_URL = "https://api.github.com/"

val networkModule = module{
    single<Retrofit>  {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create<UserService>()
    }
}