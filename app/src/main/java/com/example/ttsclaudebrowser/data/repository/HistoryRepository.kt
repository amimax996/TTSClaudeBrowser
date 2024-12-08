package com.example.ttsclaudebrowser.data.repository

import com.example.ttsclaudebrowser.data.local.HistoryDao
import com.example.ttsclaudebrowser.data.local.HistoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val historyDao: HistoryDao
) {
    suspend fun insertHistory(history: HistoryEntity) {
        historyDao.insertHistory(history)
    }

    fun getAllHistory(): Flow<List<HistoryEntity>> {
        return historyDao.getAllHistory()
    }

    suspend fun deleteAllHistory() {
        historyDao.deleteAllHistory()
    }
}