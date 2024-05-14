package com.dicoding.cindy.storyapp.di

import android.content.Context
import com.dicoding.cindy.storyapp.data.StoryRepository
import com.dicoding.cindy.storyapp.data.pref.UserPreference
import com.dicoding.cindy.storyapp.data.pref.dataStore
import com.dicoding.cindy.storyapp.data.retrofit.ApiConfig
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.first

object Injection {
    fun provideRepository(context: Context): StoryRepository {
         val pref = UserPreference.getInstance(context.dataStore)
         val user = runBlocking { pref.getUser().first() }
         val apiService = ApiConfig.getApiService(user.token)
         return StoryRepository.getInstance(apiService, pref)
    }
}