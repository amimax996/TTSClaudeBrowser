package com.example.ttsclaudebrowser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ttsclaudebrowser.data.local.HistoryEntity
import com.example.ttsclaudebrowser.ui.viewmodels.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val historyList by viewModel.historyList.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("履歴") })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(historyList) { history ->
                HistoryItem(history)
            }
        }
    }
}

@Composable
fun HistoryItem(history: HistoryEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(history.content)
            Spacer(modifier = Modifier.height(4.dp))
            // 日時をフォーマット
            Text(
                SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(
                    Date(history.createdAt)
                )
            )
        }
    }
}