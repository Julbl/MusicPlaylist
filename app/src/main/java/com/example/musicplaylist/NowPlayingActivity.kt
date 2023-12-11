package com.example.musicplaylist

import android.os.Bundle
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity

class NowPlayingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_playing)

        val prevTrackButton: ImageButton = findViewById(R.id.prevTrackButton)
        val playPauseButton: ImageButton = findViewById(R.id.playPauseButton)
        val nextTrackButton: ImageButton = findViewById(R.id.nextTrackButton)
        val volumeSeekBar: SeekBar = findViewById(R.id.volumeSeekBar)

        // TODO: Добавьте обработчики нажатия для кнопок и обновите громкость
    }
}