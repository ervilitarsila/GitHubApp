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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun getDetailUserSelected(userName: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val user = withContext(Dispatchers.IO) {
                    userService.getUser(userName)
                }
                _userSelected.postValue(user)
                _errorMessage.postValue(null)
            } catch (e: Exception) {
                _userSelected.postValue(null)
                _errorMessage.postValue("Failed to fetch user details. Please try again.")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun getDetailUserSelectedRepositories(userName: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val listRepositories = withContext(Dispatchers.IO) {
                    userService.getRepositories(userName)
                }
                _listUserRepositories.postValue(listRepositories)
                _errorMessage.postValue(null)
            } catch (e: Exception) {
                _listUserRepositories.postValue(emptyList())
                _errorMessage.postValue("Failed to fetch user repositories. Please try again.")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
