package com.ervilitasila.githubapp.di

import com.ervilitasila.githubapp.ui.home.UserViewModel
import com.ervilitasila.githubapp.ui.userdetail.UserDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { UserViewModel(get()) }
    viewModel { UserDetailViewModel(get()) }
}