package xyz.ivaniskandar.yuzu.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import xyz.ivaniskandar.yuzu.util.Prefs

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_LOCKED_BOOT_COMPLETED) {
            if (Prefs(context).masterEnabled) {
                ContextCompat.startForegroundService(context, Intent(context, BootService::class.java))
            }
        }
    }
}