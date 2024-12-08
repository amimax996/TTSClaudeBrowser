package com.example.ttsclaudebrowser.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ttsclaudebrowser.model.Bookmark

@Database(entities = [Bookmark::class], version = 1, exportSchema = false)
abstract class BookmarkDatabase : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao

    abstract fun historyDao(): HistoryDao

    companion object {
        const val DATABASE_NAME = "bookmark_database"
    }
}