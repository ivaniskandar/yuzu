package xyz.ivaniskandar.yuzu.util

import android.content.Context
import androidx.core.content.edit
import xyz.ivaniskandar.yuzu.R

class Prefs(private val context: Context) {
    private val prefs = context.createDeviceProtectedStorageContext()
        .getSharedPreferences("${context.packageName}.main.config", Context.MODE_PRIVATE)

    var masterEnabled: Boolean
        get() = prefs.getBoolean(MASTER_ENABLED, false)
        set(value) = prefs.edit { putBoolean(MASTER_ENABLED, value) }

    var soundSelectionKey: String
        get() = prefs.getString(SOUND_SELECTION, context.resources.getStringArray(R.array.sound_options_key)[0])!!
        set(value) = prefs.edit { putString(SOUND_SELECTION, value) }

    val soundSelectionTitle: String
        get() {
            val keyArray = context.resources.getStringArray(R.array.sound_options_key)
            val titleArray = context.resources.getStringArray(R.array.sound_options_title)
            return titleArray[keyArray.indexOf(soundSelectionKey)]
        }

    companion object {
        private const val MASTER_ENABLED = "master_enabled"
        private const val SOUND_SELECTION = "sound_selection"
    }
}