package com.example.ttsclaudebrowser.util

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.runtime.*
import java.util.*

class TTSHelper(private val context: Context) {

    private var tts: TextToSpeech? = null
    private var isTtsReady by mutableStateOf(false)

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isTtsReady = true
                // 言語を設定 (日本語)
                tts?.language = Locale.JAPANESE
            }
        }

        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                // 読み上げ開始時の処理 (必要であれば)
            }

            override fun onDone(utteranceId: String?) {
                // 読み上げ完了時の処理 (必要であれば)
            }

            override fun onError(utteranceId: String?) {
                // エラー発生時の処理 (必要であれば)
            }
        })
    }

    fun speak(text: String) {
        if (isTtsReady) {
            val params = Bundle()
            params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "utteranceId")
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, params, "utteranceId")
        }
    }

    fun stop() {
        tts?.stop()
    }

    fun shutdown() {
        tts?.shutdown()
    }

    fun getAvailableEngines(): List<String> {
        return tts?.engines?.map { it.name } ?: emptyList()
    }

    fun getDefaultEngine(): String {
        return tts?.defaultEngine?.name ?: ""
    }

    fun setDefaultEngine(engineName: String) {
        val engine = tts?.engines?.find { it.name == engineName }
        if (engine != null) {
            tts?.defaultEngine = engine
        }
    }

    fun getPitch(): Float {
        return tts?.voice?.pitch ?: 1.0f
    }

    fun setPitch(pitch: Float) {
        tts?.setPitch(pitch)
    }

    fun getSpeechRate(): Float {
        return tts?.voice?.speechRate ?: 1.0f
    }

    fun setSpeechRate(speechRate: Float) {
        tts?.setSpeechRate(speechRate)
    }
}