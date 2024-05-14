package com.dicoding.cindy.storyapp.data

import com.dicoding.cindy.storyapp.data.pref.UserModel
import com.dicoding.cindy.storyapp.data.pref.UserPreference
import com.dicoding.cindy.storyapp.data.response.login.LoginResponse
import com.dicoding.cindy.storyapp.data.retrofit.ApiService
import com.dicoding.cindy.storyapp.data.response.signup.SignupResponse
import kotlinx.coroutines.flow.Flow

class StoryRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
){
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getUser(): Flow<UserModel> {
        return userPreference.getUser()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun signUpUser(name: String, email: String, password: String): Result<SignupResponse> {
        return try {
            val response = apiService.userSignUp(name, email, password)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun loginUser(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.userLogin(email, password)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
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