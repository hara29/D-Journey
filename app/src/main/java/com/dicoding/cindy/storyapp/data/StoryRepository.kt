package com.dicoding.cindy.storyapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.cindy.storyapp.data.response.ErrorResponse
import com.dicoding.cindy.storyapp.data.response.login.LoginResponse
import com.dicoding.cindy.storyapp.data.response.login.LoginResult
import com.dicoding.cindy.storyapp.data.retrofit.ApiService
import com.dicoding.cindy.storyapp.data.response.signup.SignupResponse
import com.dicoding.cindy.storyapp.data.response.story.AddNewStoryResponse
import com.dicoding.cindy.storyapp.data.response.story.GetAllStoriesResponse
import com.dicoding.cindy.storyapp.data.retrofit.ApiConfig
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

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
        } catch (e: HttpException) {
            emit(handleHttpException(e))
        }
    }

    fun loginUser(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.userLogin(email, password)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(handleHttpException(e))
        }
    }

    fun getStories(token: String): LiveData<Result<GetAllStoriesResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = ApiConfig.getApiService(token).getStories()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(handleHttpException(e))
        }
    }

    fun uploadStory(token: String, imageFile: File, description: String): LiveData<Result<AddNewStoryResponse>> = liveData {
        emit(Result.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )
        try {
            val response = ApiConfig.getApiService(token).uploadStory(multipartBody, requestBody)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(handleHttpException(e))
        }

    }
    private fun handleHttpException(e: HttpException): Result.Error {
        val jsonInString = e.response()?.errorBody()?.string()
        val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
        val errorMessage = errorBody.message
        val errorText= "An error occurred"
        return Result.Error(errorMessage ?: errorText)
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