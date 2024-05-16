package com.dicoding.cindy.storyapp.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.cindy.storyapp.data.StoryRepository
import com.dicoding.cindy.storyapp.data.response.signup.SignupResponse
import com.dicoding.cindy.storyapp.data.Result
import com.dicoding.cindy.storyapp.data.response.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException

class SignupViewModel(private val repository: StoryRepository) : ViewModel() {
    fun signUpUser(name: String, email: String, password: String): LiveData<Result<SignupResponse>> {
//        try {
//            //get success message
//            val message = repository.signupUser(name, email, password)
//        } catch (e: HttpException) {
//            //get error message
//            val jsonInString = e.response()?.errorBody()?.string()
//            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
//            val errorMessage = errorBody.message
//        }
         return repository.signupUser(name, email, password)
    }
}