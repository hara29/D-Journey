package com.dicoding.cindy.storyapp.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.cindy.storyapp.data.StoryRepository
import com.dicoding.cindy.storyapp.data.remote.response.signup.SignupResponse
import com.dicoding.cindy.storyapp.data.Result

class SignupViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _signupResult = MutableLiveData<Result<SignupResponse>>()
    val signupResult: LiveData<Result<SignupResponse>> = _signupResult
    fun signUpUser(name: String, email: String, password: String) {
        repository.signupUser(name, email, password).observeForever {
            _signupResult.value = it
        }
    }
}