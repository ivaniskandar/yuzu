package xyz.ivaniskandar.yuzu.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import xyz.ivaniskandar.yuzu.R
import xyz.ivaniskandar.yuzu.util.getBootMediaPlayer

class BootService : Service() {
    companion object {
        private const val CHANNEL_GENERAL = "general"
        private const val FOREGROUND_ID = 14045
    }

    private var mediaPlayer: MediaPlayer? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.getSystemService(this, NotificationManager::class.java)!!.run {
                val channel = NotificationChannel(
                        CHANNEL_GENERAL,
                        getString(R.string.notification_general_title),
                        IMPORTANCE_DEFAULT
                )
                channel.setSound(null, null)
                createNotificationChannel(channel)
            }
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_GENERAL).apply {
            setShowWhen(false)
            setSmallIcon(R.drawable.ic_sound)
            setContentTitle(getString(R.string.boot_notification_title))
        }.build()
        startForeground(FOREGROUND_ID, notification)

        mediaPlayer = getBootMediaPlayer(this) { mp ->
            mp.release()
            stopForeground(true)
        }.apply {
            prepare()
            start()
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null
}