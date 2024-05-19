package com.ervilitasila.githubapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ervilitasila.githubapp.model.User
import com.ervilitasila.githubapp.network.UserApi
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private var _listUsers = MutableLiveData<List<User>>()
    val listUsers : LiveData<List<User>> get() = _listUsers

    init {
        getListUsers()
    }
    fun getListUsers() {
        viewModelScope.launch{
            try {
                val users = UserApi.retrofitService.getUsers()
                Log.i("etbs", "USER=${users.size}")
                _listUsers.postValue(users)
            } catch (e: Exception) {
                Log.e("etbs", "Erro ao obter usuários", e)
            }
        }
    }
}