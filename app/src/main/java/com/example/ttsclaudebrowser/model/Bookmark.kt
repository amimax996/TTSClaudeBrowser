package com.example.ttsclaudebrowser.model

data class Bookmark(
    val id: Int = 0,
    val title: String,
    val url: String,
    val note: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)