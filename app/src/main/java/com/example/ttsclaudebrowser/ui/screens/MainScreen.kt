package com.example.ttsclaudebrowser.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ttsclaudebrowser.ui.components.ClaudeWebView
import com.example.ttsclaudebrowser.ui.components.ScrollState
import com.example.ttsclaudebrowser.ui.components.WebViewState
import com.example.ttsclaudebrowser.ui.viewmodels.MainViewModel
import com.example.ttsclaudebrowser.util.TTSHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navController: NavController
) {
    val webViewState = remember {
        val url = navController.currentBackStackEntry?.arguments?.getString("url")
            ?: "https://claude.ai/new"
        WebViewState(url = url)
    }
    var isFABVisible by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val ttsHelper = remember { TTSHelper(context) }

    Scaffold(
        floatingActionButton = {
            if (isFABVisible) {
                FloatingActionButton(onClick = { viewModel.showBookmarkDialog() }) {
                    Icon(
                        imageVector = Icons.Filled.BookmarkAdd,
                        contentDescription = "Bookmark"
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            ClaudeWebView(
                webViewState = webViewState,
                onScrollStateChanged = { isFABVisible = it == ScrollState.IDLE },
                ttsHelper = ttsHelper, // TTSHelper を渡す
                historyRepository = hiltViewModel() // HistoryRepository を注入
            )
        }

        // ブックマーク登録ダイアログ
        if (viewModel.showBookmarkDialog.value) {
            AlertDialog(
                onDismissRequest = { viewModel.dismissBookmarkDialog() },
                title = { Text("ブックマーク登録") },
                text = {
                    Column {
                        TextField(
                            value = viewModel.bookmarkTitle.value,
                            onValueChange = { viewModel.bookmarkTitle.value = it },
                            label = { Text("タイトル") }
                        )
                        TextField(
                            value = viewModel.bookmarkNote.value,
                            onValueChange = { viewModel.bookmarkNote.value = it },
                            label = { Text("メモ") }
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        viewModel.saveBookmark(webViewState.url)
                        viewModel.dismissBookmarkDialog()
                    }) {
                        Text("保存")
                    }
                },
                dismissButton = {
                    Button(onClick = { viewModel.dismissBookmarkDialog() }) {
                        Text("キャンセル")
                    }
                }
            )
        }
    }
}