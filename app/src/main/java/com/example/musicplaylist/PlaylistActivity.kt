package com.example.musicplaylist

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class PlaylistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.playlist_item)

        val playlistTitleTextView: TextView = findViewById(R.id.albumTitleTextView)
        val recyclerViewTracks: RecyclerView = findViewById(R.id.recyclerViewTracks)

        // Получение данных о плейлисте из Intent
        val playlist = intent.getSerializableExtra("playlist") as Playlist

        // Настройка заголовка плейлиста
        playlistTitleTextView.text = playlist.name

        val trackAdapter = TrackAdapter(this, playlist.tracks)
        recyclerViewTracks.adapter = trackAdapter

    }
}
