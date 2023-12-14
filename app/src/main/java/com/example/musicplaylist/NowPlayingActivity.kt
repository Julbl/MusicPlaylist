package com.example.musicplaylist

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable



class NowPlayingActivity : AppCompatActivity() {
    private lateinit var playlist: List<MusicTrack>
    private lateinit var mediaPlayer: MediaPlayer
    private var currentTrackIndex: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_playing)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Log.i("NowPlayingActivity", "onCreate: NowPlayingActivity created")
        playlist = intent.getSerializableExtra("playlist") as? List<MusicTrack> ?: emptyList()
        currentTrackIndex = intent.getIntExtra("currentTrackIndex", 0)
        //Log.i("NowPlayingActivity", "Received track: ${playlist[currentTrackIndex].title}")


        if (playlist.isEmpty()) {
            // Обработка ситуации, когда плейлист пуст
            finish()
        } else {
            setupUI()
            playTrack(currentTrackIndex)
            val currentTrack = playlist.getOrNull(currentTrackIndex)
            if (currentTrack != null) {
                Log.i("NowPlayingActivity", "Current Track: ${currentTrack.title}")
            }
        }
        val track = intent.getSerializableExtra("track") as? MusicTrack
        if (track != null) {
            // Используйте информацию о треке для обновления интерфейса

            // Пример обновления заголовка
            val titleTextView: TextView = findViewById(R.id.nowPlayingTitleTextView)
            titleTextView.text = track.title

            // Пример обновления исполнителя
            val artistTextView: TextView = findViewById(R.id.nowPlayingArtistTextView)
            artistTextView.text = track.artist

            // Пример обновления изображения обложки
            val coverImageView: ImageView = findViewById(R.id.nowPlayingCoverImageView)
            coverImageView.setImageResource(track.imageResourse)

            // Другие обновления, если необходимо
        }
    }

    private fun setupUI() {
        // Инициализация UI-элементов и обработчиков кнопок

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
        updateCoverImage(currentTrackIndex)
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
    private fun updateCoverImage(trackIndex: Int) {
        val coverImageView: ImageView = findViewById(R.id.nowPlayingCoverImageView)
    }

}

