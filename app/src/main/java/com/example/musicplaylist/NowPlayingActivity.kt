package com.example.musicplaylist

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity

class NowPlayingActivity : AppCompatActivity() {
    private lateinit var playlist: List<MusicTrack>
    private lateinit var mediaPlayer: MediaPlayer
    private var currentTrackIndex: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_playing)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        playlist = intent.getSerializableExtra("playlist") as? List<MusicTrack> ?: emptyList()

        if (playlist.isEmpty()) {
            // Обработка ситуации, когда плейлист пуст
            finish()
        } else {
            setupUI()
            playTrack(currentTrackIndex)
        }
//        val playButton: Button = findViewById(R.id.playButton)
//
//        // Устанавливаем слушатель кликов для кнопки Play
//        playButton.setOnClickListener {
//            PanelManager.onPlayButtonClicked(this)
//        }
        PanelManager.setOnClickListener(View.OnClickListener {
            // Здесь можно добавить код для перехода в NowPlayingActivity
            // Например, открыть NowPlayingActivity
            val intent = Intent(this, NowPlayingActivity::class.java)
            startActivity(intent)
        })
    }

    private fun setupUI() {
        // Инициализация UI-элементов и обработчиков кнопок
        // Например, кнопки play, pause, next, prev и другие элементы интерфейса
        // Установка обработчиков событий для кнопок

        mediaPlayer = MediaPlayer()

        val playPauseButton: ImageButton = findViewById(R.id.playPauseButton)
        playPauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                pauseTrack()
            } else {
                resumeTrack()
            }
        }

        val nextTrackButton: ImageButton = findViewById(R.id.nextTrackButton)
        nextTrackButton.setOnClickListener {
            playNextTrack()
        }

        val prevTrackButton: ImageButton = findViewById(R.id.prevTrackButton)
        prevTrackButton.setOnClickListener {
            playPrevTrack()
        }

        val volumeSeekBar: SeekBar = findViewById(R.id.volumeSeekBar)
        volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                setVolume(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun playTrack(trackIndex: Int) {
        if (trackIndex >= 0 && trackIndex < playlist.size) {
            // Очищаем предыдущий трек и создаем новый MediaPlayer
            mediaPlayer.release()
            val rawResourceId = resources.getIdentifier(playlist[trackIndex].audioFileName, "raw", packageName)
            mediaPlayer = MediaPlayer.create(this, rawResourceId)
            mediaPlayer.start()
        }
    }


    private fun pauseTrack() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    private fun resumeTrack() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    private fun playNextTrack() {
        currentTrackIndex = (currentTrackIndex + 1) % playlist.size
        playTrack(currentTrackIndex)
    }

    private fun playPrevTrack() {
        currentTrackIndex = (currentTrackIndex - 1 + playlist.size) % playlist.size
        playTrack(currentTrackIndex)
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.release()
    }

    private fun togglePlayPause() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        } else {
            mediaPlayer?.start()
        }
    }

    private fun setVolume(volume: Int) {
        val volumeFloat = volume / 100f
        mediaPlayer?.setVolume(volumeFloat, volumeFloat)
    }

}

