package com.example.ttsclaudebrowser

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    // 必要に応じて、アプリ全体で共有するリソースや状態を定義
}