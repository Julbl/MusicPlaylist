package com.example.musicplaylist

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

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

        // Так запускается активность NowPlayingActivity только без данных
        /*val trackAdapter = TrackAdapter(this, playlist.tracks) { selectedTrack ->
            // Обработка выбора трека
            val intent = Intent(this, NowPlayingActivity::class.java)
            intent.putExtra("playlist", playlist.tracks as Serializable)
            intent.putExtra("currentTrackIndex", playlist.tracks.indexOf(selectedTrack))
            Log.i("TrackAdapter", "Opening NowPlayingActivity for track: ${selectedTrack.title}")
            startActivity(intent)
        }*/
        val trackAdapter = TrackAdapter(this, playlist.tracks) { selectedTrack ->
            // Обработка выбора трека
            val intent = Intent(this, NowPlayingActivity::class.java)
            intent.putExtra("playlist", playlist)
            //intent.putExtra("currentTrackIndex", currentTrackIndex)
            intent.putExtra("track", selectedTrack)
            startActivity(intent)
           /* val intent = Intent(this, NowPlayingActivity::class.java)
            intent.putExtra("playlist", playlist.tracks as Serializable)
            intent.putExtra("currentTrackIndex", playlist.tracks.indexOf(selectedTrack))
            intent.putExtra("selectedTrack", selectedTrack) // Добавляем информацию о выбранном треке
            Log.i("TrackAdapter", "Opening NowPlayingActivity for track: ${selectedTrack.title}")
            startActivity(intent)*/
        }


        recyclerViewTracks.layoutManager = LinearLayoutManager(this)
        recyclerViewTracks.adapter = trackAdapter
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
