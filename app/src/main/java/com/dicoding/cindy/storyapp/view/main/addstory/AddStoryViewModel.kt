package com.dicoding.cindy.storyapp.view.main.addstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.cindy.storyapp.data.Result
import com.dicoding.cindy.storyapp.data.StoryRepository
import com.dicoding.cindy.storyapp.data.remote.response.login.LoginResult
import com.dicoding.cindy.storyapp.data.remote.response.story.AddNewStoryResponse
import java.io.File

class AddStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getSession(): LiveData<LoginResult> {
        return repository.getUser().asLiveData()
    }
    fun postStory(token: String, file: File, description: String): LiveData<Result<AddNewStoryResponse>> {
        return repository.uploadStory(token, file,description)
    }
}