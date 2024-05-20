package com.dicoding.cindy.storyapp.view.main.detailstory

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.cindy.storyapp.data.response.story.ListStoryItem
import com.dicoding.cindy.storyapp.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
    }

    private fun getData(){
        val story = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<ListStoryItem>(EXTRA_STORY, ListStoryItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<ListStoryItem>(EXTRA_STORY)
        }
        if (story != null) {
            Glide.with(applicationContext)
                .load(story.photoUrl)
                .into(binding.ivDetailPhoto)
            binding.tvDetailName.text = story.name
            binding.tvDetailDescription.text = story.description
        }

    }
    companion object{
        const val EXTRA_STORY = "extra_story"
    }
}