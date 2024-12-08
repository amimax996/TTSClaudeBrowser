package com.example.ttsclaudebrowser.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ttsclaudebrowser.model.Bookmark
import com.example.ttsclaudebrowser.ui.viewmodels.BookmarkViewModel

@Composable
fun BookmarkScreen(
    navController: NavController,
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    val bookmarks by viewModel.bookmarks.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("ブックマーク") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(bookmarks) { bookmark ->
                    BookmarkItem(bookmark, navController, viewModel)
                }
            }

            // 編集ダイアログ
            if (viewModel.showEditDialog.value) {
                AlertDialog(
                    onDismissRequest = { viewModel.dismissEditDialog() },
                    title = { Text("ブックマーク編集") },
                    text = {
                        Column {
                            TextField(
                                value = viewModel.editTitle.value,
                                onValueChange = { viewModel.editTitle.value = it },
                                label = { Text("タイトル") }
                            )
                            TextField(
                                value = viewModel.editNote.value,
                                onValueChange = { viewModel.editNote.value = it },
                                label = { Text("メモ") }
                            )
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.updateBookmark()
                            viewModel.dismissEditDialog()
                        }) {
                            Text("保存")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { viewModel.dismissEditDialog() }) {
                            Text("キャンセル")
                        }
                    }
                )
            }

            // 削除ダイアログ
            if (viewModel.showDeleteDialog.value) {
                AlertDialog(
                    onDismissRequest = { viewModel.dismissDeleteDialog() },
                    title = { Text("ブックマーク削除") },
                    text = { Text("本当に削除しますか？") },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.deleteBookmark()
                            viewModel.dismissDeleteDialog()
                        }) {
                            Text("削除")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { viewModel.dismissDeleteDialog() }) {
                            Text("キャンセル")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BookmarkItem(bookmark: Bookmark, navController: NavController, viewModel: BookmarkViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("main_screen/${bookmark.url}")
            }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(bookmark.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(bookmark.url, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(4.dp))
                if (bookmark.note != null) {
                    Text("メモ: ${bookmark.note}", style = MaterialTheme.typography.bodyMedium)
                }
            }
            Row {
                IconButton(onClick = { viewModel.showEditDialog(bookmark) }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { viewModel.showDeleteDialog(bookmark) }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}