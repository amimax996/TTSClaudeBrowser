package com.example.ttsclaudebrowser.data.repository

import com.example.ttsclaudebrowser.data.local.BookmarkDao
import com.example.ttsclaudebrowser.model.Bookmark
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkRepository @Inject constructor(
    private val bookmarkDao: BookmarkDao
) {

    suspend fun insertBookmark(bookmark: Bookmark) {
        bookmarkDao.insertBookmark(bookmark)
    }

    suspend fun updateBookmark(bookmark: Bookmark) {
        bookmarkDao.updateBookmark(bookmark)
    }

    suspend fun deleteBookmark(bookmark: Bookmark) {
        bookmarkDao.deleteBookmark(bookmark)
    }

    fun getAllBookmarks(): Flow<List<Bookmark>> {
        return bookmarkDao.getAllBookmarks()
    }
}