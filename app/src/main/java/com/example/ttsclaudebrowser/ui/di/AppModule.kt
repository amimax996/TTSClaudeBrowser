package com.example.ttsclaudebrowser.ui.di

import android.content.Context
import androidx.room.Room
import com.example.ttsclaudebrowser.data.local.BookmarkDao
import com.example.ttsclaudebrowser.data.local.BookmarkDatabase
import com.example.ttsclaudebrowser.data.local.HistoryDao
import com.example.ttsclaudebrowser.data.repository.BookmarkRepository
import com.example.ttsclaudebrowser.data.repository.HistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBookmarkDatabase(@ApplicationContext context: Context): BookmarkDatabase {
        return Room.databaseBuilder(
            context,
            BookmarkDatabase::class.java,
            "bookmark_database"
        ).build()
    }

    @Provides
    fun provideBookmarkDao(database: BookmarkDatabase): BookmarkDao {
        return database.bookmarkDao()
    }

    @Provides
    @Singleton
    fun provideBookmarkRepository(bookmarkDao: BookmarkDao): BookmarkRepository {
        return BookmarkRepository(bookmarkDao)
    }

    @Provides
    fun provideHistoryDao(database: BookmarkDatabase): HistoryDao {
        return database.historyDao()
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(historyDao: HistoryDao): HistoryRepository {
        return HistoryRepository(historyDao)
    }
}