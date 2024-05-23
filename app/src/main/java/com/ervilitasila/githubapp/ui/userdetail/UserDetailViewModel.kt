package com.ervilitasila.githubapp.ui.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ervilitasila.githubapp.model.Repository
import com.ervilitasila.githubapp.model.UserProfile
import com.ervilitasila.githubapp.network.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailViewModel(private val userService: UserService) : ViewModel() {
    private val _userSelected = MutableLiveData<UserProfile?>()
    val userSelected: LiveData<UserProfile?> get() = _userSelected

    private val _listUserRepositories = MutableLiveData<List<Repository>>()
    val listUserRepositories: LiveData<List<Repository>> get() = _listUserRepositories

    fun getDetailUserSelected(userName: String) {
        viewModelScope.launch {
            try {
                val user = withContext(Dispatchers.IO) {
                    userService.getUser(userName)
                }
                _userSelected.postValue(user)
            } catch (e: Exception) {
                _userSelected.postValue(null)
            }
        }
    }

    fun getDetailUserSelectedRepositories(userName: String) {
        viewModelScope.launch {
            try {
                val listRepositories = withContext(Dispatchers.IO) {
                    userService.getRepositories(userName)
                }
                _listUserRepositories.postValue(listRepositories)
            } catch (e: Exception) {
                _listUserRepositories.postValue(emptyList())
            }
        }
    }
}
