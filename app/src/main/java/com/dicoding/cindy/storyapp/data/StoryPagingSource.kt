package com.dicoding.cindy.storyapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.cindy.storyapp.data.response.story.ListStoryItem
import com.dicoding.cindy.storyapp.data.retrofit.ApiConfig
import com.dicoding.cindy.storyapp.data.retrofit.ApiService

class StoryPagingSource(private val apiConfig: ApiConfig, private val token: String) : PagingSource<Int, ListStoryItem>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            // val responseData = apiService.getStories(position, params.loadSize).listStory
            val responseData = apiConfig.getApiService(token).getStories(position, params.loadSize).listStory
            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}