package com.dicoding.cindy.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.cindy.storyapp.data.StoryRepository
import com.dicoding.cindy.storyapp.data.remote.response.login.LoginResult
import com.dicoding.cindy.storyapp.data.remote.response.story.ListStoryItem
import kotlinx.coroutines.launch

class ListStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getStories(token: String): LiveData<PagingData<ListStoryItem>> =
        repository.getStories(token).cachedIn(viewModelScope)

    fun getSession(): LiveData<LoginResult> {
        return repository.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}