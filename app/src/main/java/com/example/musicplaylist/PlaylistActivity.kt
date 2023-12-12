package com.example.musicplaylist

import android.media.MediaPlayer
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlaylistActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.playlist_item)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val playlistTitleTextView: TextView = findViewById(R.id.albumTitleTextView)
        val recyclerViewTracks: RecyclerView = findViewById(R.id.recyclerViewTracks)

        // Получение данных о плейлисте из Intent
        val playlist = intent.getSerializableExtra("playlist") as Playlist

        // Настройка заголовка плейлиста
        playlistTitleTextView.text = playlist.name

        val trackAdapter = TrackAdapter(this, playlist.tracks)
        recyclerViewTracks.layoutManager = LinearLayoutManager(this)
        recyclerViewTracks.adapter = trackAdapter

/*        val firstTrack = playlist.tracks.firstOrNull()
        firstTrack?.let {
            val resourceId = resources.getIdentifier("asphalt", "raw", "com.example.musicplaylist")
            val mediaPlayer = MediaPlayer.create(this, resourceId)
            mediaPlayer?.start()
        }*/
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.release()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
