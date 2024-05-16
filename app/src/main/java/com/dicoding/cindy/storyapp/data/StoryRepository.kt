package com.dicoding.cindy.storyapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.cindy.storyapp.data.response.ErrorResponse
import com.dicoding.cindy.storyapp.data.response.login.LoginResponse
import com.dicoding.cindy.storyapp.data.response.login.LoginResult
import com.dicoding.cindy.storyapp.data.retrofit.ApiService
import com.dicoding.cindy.storyapp.data.response.signup.SignupResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class StoryRepository (
    private val apiService: ApiService,
    private val userPreference: UserPreference
){
    suspend fun saveSession(user: LoginResult) {
        userPreference.saveSession(user)
    }

    fun getUser(): Flow<LoginResult> {
        return userPreference.getUser()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun signupUser(name: String, email: String, password: String): LiveData<Result<SignupResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.userSignup(name, email, password)
            emit(Result.Success(response))
            Log.d("Signup",  "Berhasil signup")
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage ?: "An error occurred"))
            Log.d("Signup", e.message?: "Tidak bisa signup")
        }
    }

    fun loginUser(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.userLogin(email, password)
            emit(Result.Success(response))
            Log.d("Login",  "Berhasil login")
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage ?: "An error occurred"))
            Log.d("login", e.message?: "Tidak bisa login")
        }
//        emit(Result.Loading)
//        try {
//            val response = apiService.userLogin(email, password)
//            emit(Result.Success(response))
//        } catch (e: Exception) {
//            emit(Result.Error(e.message ?: "An error occurred"))
//        }
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, userPreference)
            }.also { instance = it }

    }
}