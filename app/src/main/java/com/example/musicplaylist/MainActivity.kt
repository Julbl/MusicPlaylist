package com.example.musicplaylist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import com.example.musicplaylist.PlaylistAdapter


class MainActivity : ComponentActivity() {
    private lateinit var constraintLayout: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("MainActivity", "onCreate")

        constraintLayout = findViewById(R.id.constraintLayout)
        val searchView: SearchView = findViewById(R.id.searchView)

        // Replace this with your actual playlist data retrieval logic
        val playlists = getSamplePlaylists()

        // Add playlist items dynamically
        for (playlist in playlists) {
            addPlaylistItem(playlist)
        }

        // Handle searchView changes (you may want to filter the playlists here)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search submission
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search text changes
                return true
            }
        })
    }

    private fun addPlaylistItem(playlist: Playlist) {
        val playlistItemView = layoutInflater.inflate(R.layout.playlist_item, null)

        // Find ImageView and TextView in the playlist_item layout
        val playlistImageView: ImageView = playlistItemView.findViewById(R.id.playlistImageView)
        val playlistTitleTextView: TextView = playlistItemView.findViewById(R.id.playlistTitleTextView)

        // Set playlist image and title
        playlistImageView.setImageResource(playlist.coverImageUrl)
        playlistTitleTextView.text = playlist.name

        // Set a click listener for the playlist item
        playlistItemView.setOnClickListener {
            openPlaylistActivity(playlist)
        }

        // Add the playlist item to the ConstraintLayout
        constraintLayout.addView(playlistItemView)
    }

    // Метод для открытия активности с треками выбранного плейлиста
    private fun openPlaylistActivity(playlist: Playlist) {
        Log.i("MainActivity", "Opening PlaylistActivity for playlist: ${playlist.name}")
        val intent = Intent(this, PlaylistActivity::class.java)
        intent.putExtra("playlist", playlist)
        startActivity(intent)
    }

    // Замените этот код на ваш фактический метод получения данных о плейлистах
    private fun getSamplePlaylists(): List<Playlist> {
        return listOf(
            Playlist("Мелодии для сна",  R.drawable.playlist_for_sleeping, emptyList()),
            Playlist("Для хорошего настроения",  R.drawable.playlist_for_goodmood, tracks = getSampleTracksForPlaylistGoodMood()),
            Playlist("Когда грустно",  R.drawable.playlist_when_ur_sad, emptyList()),
            Playlist("Классика",  R.drawable.playlist_for_classic, emptyList()),
        )
    }
}
