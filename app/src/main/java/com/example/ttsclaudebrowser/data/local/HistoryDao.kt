package com.example.ttsclaudebrowser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: HistoryEntity)

    @Query("SELECT * FROM history ORDER BY createdAt DESC")
    fun getAllHistory(): Flow<List<HistoryEntity>>

    @Query("DELETE FROM history")
    suspend fun deleteAllHistory()
}