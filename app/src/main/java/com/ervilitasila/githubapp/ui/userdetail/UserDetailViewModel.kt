package com.ervilitasila.githubapp.ui.userdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ervilitasila.githubapp.model.Repository
import com.ervilitasila.githubapp.model.UserProfile
import com.ervilitasila.githubapp.network.UserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailViewModel: ViewModel() {
    private val _userSelected: MutableLiveData<UserProfile> by lazy {
        MutableLiveData<UserProfile>()
    }
    val userSelected: LiveData<UserProfile> get() = _userSelected

    private val _listUserRepositories: MutableLiveData<List<Repository>> by lazy {
        MutableLiveData<List<Repository>>()
    }
    val listUserRepositories: LiveData<List<Repository>> get() = _listUserRepositories


    fun getDetailUserSelected(userName: String) {
        viewModelScope.launch {
            try {
                val user = withContext(Dispatchers.IO) {
                    UserApi.retrofitService.getUser(userName)
                }
                _userSelected.postValue(user)
            } catch (e: Exception) {
                Log.e("etbs", "DetailUserSelected: Erro ao obter usuários", e)
            }
        }
    }

    fun getDetailUserSelectedRepositories(userName: String) {
        viewModelScope.launch {
            try {
                val listRepositories = withContext(Dispatchers.IO) {
                    UserApi.retrofitService.getRepositories(userName)
                }
                Log.i("etbs", "DETAILUSER-REPOS={${listRepositories.size}}")
                _listUserRepositories.postValue(listRepositories)
            } catch (e: Exception) {
                Log.e("etbs", "DetailUserSelected -- REPOS: Erro ao obter usuários", e)
            }
        }
    }

}