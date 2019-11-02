package xyz.ivaniskandar.yuzu.ui

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import xyz.ivaniskandar.yuzu.R
import xyz.ivaniskandar.yuzu.util.Prefs
import xyz.ivaniskandar.yuzu.util.getBootMediaPlayer

class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val prefs = Prefs(this)
        val adapter = ArrayAdapter(
                this,
                R.layout.dropdown_menu_popup_item,
                resources.getStringArray(R.array.sound_options_title)
        )
        sound_option_dropdown.apply {
            setText(prefs.soundSelectionTitle)
            setAdapter(adapter)
            setOnItemClickListener { _, _, i, _ ->
                prefs.soundSelectionKey = resources.getStringArray(R.array.sound_options_key)[i]
                mediaPlayer?.run {
                    release()
                }
                mediaPlayer = getBootMediaPlayer(this@MainActivity)
                mediaPlayer!!.apply {
                    prepare()
                    start()
                }
            }
        }
        mainSwitch.apply {
            isChecked = prefs.masterEnabled
            setOnCheckedChangeListener { _, b ->
                prefs.masterEnabled = b
            }
        }
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.release()
    }
}
