package com.example.ttsclaudebrowser.data.local

import androidx.room.*
import com.example.ttsclaudebrowser.model.Bookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

    @Insert
    suspend fun insertBookmark(bookmark: Bookmark)

    @Update
    suspend fun updateBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Query("SELECT * FROM bookmarks")
    fun getAllBookmarks(): Flow<List<Bookmark>>
}