package com.example.ttsclaudebrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ttsclaudebrowser.ui.navigation.AppNavigation
import com.example.ttsclaudebrowser.ui.theme.TTSClaudeBrowserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TTSClaudeBrowserTheme {
                AppNavigation()
            }
        }
    }
}