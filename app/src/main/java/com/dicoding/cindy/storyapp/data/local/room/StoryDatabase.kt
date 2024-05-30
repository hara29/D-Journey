package com.dicoding.cindy.storyapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.cindy.storyapp.data.local.RemoteKeys
import com.dicoding.cindy.storyapp.data.remote.response.story.ListStoryItem

@Database(entities = [ListStoryItem::class, RemoteKeys::class], version = 2, exportSchema = false)
abstract class StoryDatabase: RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    companion object {
        @Volatile
        private var INSTANCE: StoryDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): StoryDatabase {
            if (INSTANCE == null) {
                synchronized(StoryDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        StoryDatabase::class.java, "favorite_user_database")
                        .build()
                }
            }
            return INSTANCE as StoryDatabase
        }
    }
}