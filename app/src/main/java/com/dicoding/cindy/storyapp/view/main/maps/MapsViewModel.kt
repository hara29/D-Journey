package com.dicoding.cindy.storyapp.view.main.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.cindy.storyapp.data.Result
import com.dicoding.cindy.storyapp.data.StoryRepository
import com.dicoding.cindy.storyapp.data.remote.response.story.GetAllStoriesResponse

class MapsViewModel(private val repository: StoryRepository): ViewModel() {

    fun getStoriesWithLocation(token: String): LiveData<Result<GetAllStoriesResponse>> {
        return repository.getStoriesWithLocation(token)
    }
}