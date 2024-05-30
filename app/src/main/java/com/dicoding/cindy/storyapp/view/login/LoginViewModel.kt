package com.dicoding.cindy.storyapp.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cindy.storyapp.data.Result
import com.dicoding.cindy.storyapp.data.StoryRepository
import com.dicoding.cindy.storyapp.data.remote.response.login.LoginResponse
import com.dicoding.cindy.storyapp.data.remote.response.login.LoginResult
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: StoryRepository) : ViewModel() {
    fun saveSession(user: LoginResult) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
    fun loginUser(email: String, password: String): LiveData<Result<LoginResponse>> {
        return repository.loginUser(email, password)
    }
}