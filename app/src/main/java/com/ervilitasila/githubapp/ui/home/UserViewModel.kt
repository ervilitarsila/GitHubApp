package com.ervilitasila.githubapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ervilitasila.githubapp.model.User
import com.ervilitasila.githubapp.network.UserService
import kotlinx.coroutines.launch

class UserViewModel(private val userService: UserService) : ViewModel() {

    private var _listUsers = MutableLiveData<List<User>>()
    val listUsers : LiveData<List<User>> get() = _listUsers
    private var allUsers: List<User> = listOf()

    init {
        getListUsers()
    }

    fun getListUsers() {
        viewModelScope.launch {
            try {
                val users = userService.getUsers()
                allUsers = users
                _listUsers.postValue(users)
            } catch (e: Exception) {
                _listUsers.postValue(emptyList())
            }
        }
    }

    fun filterUsers(query: String) {
        val filteredUsers = if (query.isEmpty()) {
            allUsers
        } else {
            allUsers.filter { user ->
                user.login.contains(query, ignoreCase = true)
            }
        }
        _listUsers.postValue(filteredUsers)
    }
}
