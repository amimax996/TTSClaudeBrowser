package com.example.ttsclaudebrowser.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewModelScope
import com.example.ttsclaudebrowser.data.local.HistoryEntity
import com.example.ttsclaudebrowser.data.repository.HistoryRepository
import com.example.ttsclaudebrowser.util.TTSHelper
import kotlinx.coroutines.launch

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ClaudeWebView(
    webViewState: WebViewState,
    onScrollStateChanged: (ScrollState) -> Unit,
    ttsHelper: TTSHelper, // TTSHelper を引数に追加
    historyRepository: HistoryRepository // HistoryRepository を引数に追加
) {
    var isLoading by remember { mutableStateOf(true) }
    var scrollState by remember { mutableStateOf(ScrollState.IDLE) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.javaScriptEnabled = true

                    // Cookieを有効化
                    val cookieManager = CookieManager.getInstance()
                    cookieManager.setAcceptCookie(true)
                    cookieManager.setAcceptThirdPartyCookies(this, true)

                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            isLoading = true
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            isLoading = false

                            // JavaScriptコードを読み込む
                            view?.loadUrl(
                                "javascript:(function() { " +
                                        "window.Android.getResponseText(document.getElementById('response-text').textContent);" +
                                        "})()"
                            )
                        }
                    }
                    webChromeClient = WebChromeClient()

                    setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                        scrollState = when {
                            scrollY > oldScrollY -> ScrollState.SCROLLING_DOWN
                            scrollY < oldScrollY -> ScrollState.SCROLLING_UP
                            else -> ScrollState.IDLE
                        }
                        onScrollStateChanged(scrollState)
                    }

                    // JavaScriptInterfaceを追加
                    addJavascriptInterface(WebAppInterface(context, ttsHelper, historyRepository), "Android")

                    loadUrl(webViewState.url)
                }
            }, update = { webView ->
                webView.loadUrl(webViewState.url)
            }
        )

        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}

// スクロール状態
enum class ScrollState {
    IDLE, SCROLLING_UP, SCROLLING_DOWN
}

// WebViewの状態
class WebViewState(
    var url: String
)

// JavaScriptInterfaceの定義
class WebAppInterface(
    private val context: Context,
    private val ttsHelper: TTSHelper,
    private val historyRepository: HistoryRepository
) {
    @JavascriptInterface
    fun getResponseText(responseText: String) {
        // TTSHelperを使ってレスポンスを読み上げる
        ttsHelper.speak(responseText)

        // 履歴を保存
        viewModelScope.launch {
            val history = HistoryEntity(
                content = responseText,
                createdAt = System.currentTimeMillis()
            )
            historyRepository.insertHistory(history)
        }
    }
}