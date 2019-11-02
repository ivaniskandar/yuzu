package xyz.ivaniskandar.yuzu.util

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import xyz.ivaniskandar.yuzu.R

fun getBootMediaPlayer(context: Context, onComplete: (mp: MediaPlayer) -> Unit = {}): MediaPlayer {
    val id = when (val key = Prefs(context).soundSelectionKey) {
        context.getString(R.string.sound_option_key_home) -> R.raw.home
        context.getString(R.string.sound_option_key_pixel) -> R.raw.pixel
        context.getString(R.string.sound_option_key_mac) -> R.raw.mac
        context.getString(R.string.sound_option_key_vista) -> R.raw.vista
        context.getString(R.string.sound_option_key_xp) -> R.raw.xp
        else -> throw Exception("wtf, $key is not valid sound option key")
    }
    return MediaPlayer().apply {
        setDataSource(context, Uri.parse("android.resource://${context.packageName}/${id}"))
        setAudioAttributes(AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT).build())
        setOnCompletionListener { mp -> onComplete(mp) }
    }
}