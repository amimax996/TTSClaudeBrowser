package com.example.ttsclaudebrowser.ui.screens

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ttsclaudebrowser.ui.viewmodels.SettingsViewModel
import com.example.ttsclaudebrowser.util.TTSHelper

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val ttsHelper = remember { TTSHelper(context) }

    var selectedEngine by remember { mutableStateOf(ttsHelper.getDefaultEngine()) }
    var pitch by remember { mutableStateOf(ttsHelper.getPitch()) }
    var speechRate by remember { mutableStateOf(ttsHelper.getSpeechRate()) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("設定") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // TTSエンジン選択
            TTSSettingItem(
                title = "TTSエンジン",
                value = selectedEngine,
                onValueChange = { selectedEngine = it },
                options = ttsHelper.getAvailableEngines()
            )

            // ピッチ調整
            TTSSettingItem(
                title = "ピッチ",
                value = pitch,
                onValueChange = { pitch = it },
                valueRange = 0.5f..2.0f
            )

            // 読み上げ速度調整
            TTSSettingItem(
                title = "読み上げ速度",
                value = speechRate,
                onValueChange = { speechRate = it },
                valueRange = 0.5f..2.0f
            )

            // 設定を保存するボタン
            Button(
                onClick = {
                    ttsHelper.setDefaultEngine(selectedEngine)
                    ttsHelper.setPitch(pitch)
                    ttsHelper.setSpeechRate(speechRate)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text("設定を保存")
            }
        }
    }
}

@Composable
fun <T> TTSSettingItem(
    title: String,
    value: T,
    onValueChange: (T) -> Unit,
    options: List<T>? = null,
    valueRange: ClosedFloatingPointRange<Float>? = null
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        if (options != null) {
            // ドロップダウンメニューを表示
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    readOnly = true,
                    value = value.toString(),
                    onValueChange = { },
                    label = { Text(title) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption.toString()) },
                            onClick = {
                                onValueChange(selectionOption)
                                expanded = false
                            }
                        )
                    }
                }
            }
        } else if (valueRange != null) {
            // スライダーを表示
            Slider(
                value = value as Float,
                onValueChange = { onValueChange(it as T) },
                valueRange = valueRange
            )
        }
    }
}