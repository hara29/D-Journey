package com.dicoding.cindy.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.cindy.storyapp.data.Result
import com.dicoding.cindy.storyapp.data.StoryRepository
import com.dicoding.cindy.storyapp.data.response.login.LoginResult
import com.dicoding.cindy.storyapp.data.response.story.GetAllStoriesResponse
import kotlinx.coroutines.launch

class ListStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getSession(): LiveData<LoginResult> {
        return repository.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getStories(): LiveData<Result<GetAllStoriesResponse>>{
        return repository.getStories()
    }

}