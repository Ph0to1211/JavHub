package com.jadesoft.javhub.domain

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountdownService : Service() {
    private var job: Job? = null
    private val notificationId = 1001

    override fun onBind(intent: Intent?): IBinder? = null

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        NotificationUtil.createChannel(this)
        startForeground(notificationId, buildNotification(0))

        job = CoroutineScope(Dispatchers.Default).launch {
            for (progress in 0..100 step 10) {
                delay(1000) // 1秒间隔
                updateNotification(progress)
            }
            // 倒计时完成后更新通知
            updateCompleteNotification()
            stopSelf()
        }

        return START_NOT_STICKY
    }

    private fun buildNotification(progress: Int): Notification {
        return NotificationCompat.Builder(this, NotificationUtil.CHANNEL_ID)
            .setContentTitle("倒计时中...")
            .setContentText("剩余 ${10 - (progress / 10)} 秒")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setProgress(100, progress, false)
            .setOnlyAlertOnce(true) // 只有第一次显示通知时响铃
            .build()
    }

    private fun updateNotification(progress: Int) {
        val notification = buildNotification(progress)
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
            .notify(notificationId, notification)
    }

    private fun updateCompleteNotification() {
        val notification = NotificationCompat.Builder(this, NotificationUtil.CHANNEL_ID)
            .setContentTitle("倒计时完成")
            .setContentText("10秒倒计时结束")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true) // 点击后自动消失
            .build()

        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
            .notify(notificationId, notification)
    }

    override fun onDestroy() {
        job?.cancel()
        super.onDestroy()
    }
}

object NotificationUtil {
    const val CHANNEL_ID = "countdown_channel"

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Countdown Notifications",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows countdown progress"
            }

            context.getSystemService(NotificationManager::class.java)
                ?.createNotificationChannel(channel)
        }
    }
}