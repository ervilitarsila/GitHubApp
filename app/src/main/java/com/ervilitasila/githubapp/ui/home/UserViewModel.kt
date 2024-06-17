package com.ervilitasila.githubapp.ui.home

import android.util.Log
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
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private var _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private var allUsers: List<User> = listOf()

    init {
        getListUsers()
    }

    fun getListUsers() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val users = userService.getUsers()
//                val users = setMockData()
                allUsers = users
                _listUsers.postValue(users)
                _errorMessage.postValue(null)
            } catch (e: Exception) {
                _listUsers.postValue(emptyList())
                _errorMessage.postValue("Failed to fetch user details. Please try again.")
            } finally {
                _isLoading.postValue(false)
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

    private fun setMockData()= listOf(
            User(1, "teste1", "https://avatars.githubusercontent.com/u/1?v=4", "www.google.com"),
            User(1, "teste2", "https://avatars.githubusercontent.com/u/2?v=4", "www.google.com"),
            User(1, "teste3", "https://avatars.githubusercontent.com/u/3?v=4", "www.google.com"),
            User(1, "teste4", "https://avatars.githubusercontent.com/u/4?v=4", "www.google.com"),
        )

}
