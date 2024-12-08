package com.example.ttsclaudebrowser.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ttsclaudebrowser.model.Bookmark

@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val url: String,
    val note: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)