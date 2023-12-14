package com.example.musicplaylist

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class AudioPlayerService : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playlist: List<MusicTrack>
    private var currentTrackIndex: Int = 0

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            playlist = intent.getSerializableExtra("playlist") as? List<MusicTrack> ?: emptyList()
            currentTrackIndex = intent.getIntExtra("currentTrackIndex", 0)
            playTrack(currentTrackIndex)
        }
        return START_NOT_STICKY
    }

    private fun playTrack(trackIndex: Int) {
        if (trackIndex >= 0 && trackIndex < playlist.size) {
            mediaPlayer.release()
            val rawResourceId = resources.getIdentifier(playlist[trackIndex].audioFileName, "raw", packageName)
            mediaPlayer = MediaPlayer.create(this, rawResourceId)
            mediaPlayer.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}