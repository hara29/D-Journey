package com.dicoding.cindy.storyapp.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.cindy.storyapp.data.StoryRepository
import com.dicoding.cindy.storyapp.data.response.signup.SignupResponse
import com.dicoding.cindy.storyapp.data.Result

class SignupViewModel(private val repository: StoryRepository) : ViewModel() {
    fun signUpUser(name: String, email: String, password: String): LiveData<Result<SignupResponse>> {
        return repository.signupUser(name, email, password)
    }
}