package com.jadesoft.javhub.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

object CommonUtils {

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    fun generateVideoUrls(code: String): List<String> {
        // 分离字母和数字部分（格式如 "sone-682"）
        val parts = code.split("-")
        if (parts.size != 2) return emptyList()

        val originalLetters = parts[0].lowercase()
        val numbers = parts[1]
        if (originalLetters.isEmpty() || numbers.isEmpty()) return emptyList()

        val urls = mutableListOf<String>()

        // 变体1：原字母 + 5位补零数字
        val variant1 = buildUrl(originalLetters, numbers.padStart(5, '0'))
        urls.add(variant1)

        // 变体2：前导1字母 + 5位补零数字
        val variant2Letters = "1$originalLetters"
        val variant2 = buildUrl(variant2Letters, numbers.padStart(5, '0'))
        urls.add(variant2)

        // 变体3：原字母 + 原始数字
        val variant3 = buildUrl(originalLetters, numbers)
        urls.add(variant3)

        return urls
    }

    private fun buildUrl(letters: String, numbers: String): String {
        val firstChar = letters.first().toString()
        val firstThreeLetters = letters.take(3)
        val fullCode = "$letters$numbers"
        return "https://cc3001.dmm.co.jp/litevideo/freepv/" +
                "$firstChar/$firstThreeLetters/$fullCode/${fullCode}mhb.mp4"
    }

    fun findFirstValidLink(urls: List<String>, callback: (String) -> Unit) {
        val iterator = urls.iterator()
        var isCompleted = AtomicBoolean(false) // 线程安全的状态标记

        fun checkNext() {
            if (!iterator.hasNext() || isCompleted.get()) {
                if (!isCompleted.getAndSet(true)) {
                    callback("") // 全部检查完毕且未找到有效链接
                }
                return
            }

            val url = iterator.next()
            val request = Request.Builder()
                .url(url)
                .head()
                .build()

            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // 继续检查下一个链接
                    checkNext()
                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val isValid = response.isSuccessful || response.isRedirect
                        if (isValid && !isCompleted.getAndSet(true)) {
                            callback(url) // 返回首个有效链接
                        } else {
                            checkNext() // 继续检查下一个
                        }
                    } finally {
                        response.close()
                    }
                }
            })
        }

        checkNext() // 开始检查流程
    }

    @SuppressLint("ServiceCast")
    @Composable
    fun rememberVibrator(): Vibrator {
        val context = LocalContext.current
        return remember {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }
        }
    }

    fun triggerVibration(vibrator: Vibrator, durationMillis: Long = 10) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(durationMillis)
        }
    }

}