package com.dicoding.cindy.storyapp

import com.dicoding.cindy.storyapp.data.remote.response.story.GetAllStoriesResponse
import com.dicoding.cindy.storyapp.data.remote.response.story.ListStoryItem

object DataDummy {

    fun generateDummyStoryResponse(): GetAllStoriesResponse {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                i.toString(),
                "photo + $i",
                "createdAt $i",
                "name $i",
                "desc $i",
                lat = i.toDouble() * 10,
                lon = i.toDouble() * 10,
            )
            items.add(story)
        }
        return GetAllStoriesResponse(
            error = false,
            message = "Stories fetched successfully",
            listStory = items
        )
    }
}