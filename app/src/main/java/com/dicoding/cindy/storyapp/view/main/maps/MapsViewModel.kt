package com.dicoding.cindy.storyapp.view.main.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.cindy.storyapp.data.Result
import com.dicoding.cindy.storyapp.data.StoryRepository
import com.dicoding.cindy.storyapp.data.response.login.LoginResult
import com.dicoding.cindy.storyapp.data.response.story.GetAllStoriesResponse

class MapsViewModel(private val repository: StoryRepository): ViewModel() {
    fun getSession(): LiveData<LoginResult> {
        return repository.getUser().asLiveData()
    }

    fun getStoriesWithLocation(token: String): LiveData<Result<GetAllStoriesResponse>> {
        return repository.getStoriesWithLocation(token)
    }
}