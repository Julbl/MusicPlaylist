package com.example.musicplaylist

import PlaylistAdapter
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.GridView
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class MainActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var nowPlayingPanel: View // замените на актуальный тип
    private val playlists = getSamplePlaylists()
    private val mediaPlayerManager = MediaPlayerManager.getInstance()
    private lateinit var nowPlayingTitleTextView: TextView
    private lateinit var nowPlayingArtistTextView: TextView
    private lateinit var nowPlayingCoverImageView: ImageView
    private val yourListener = object : MediaPlayerManager.OnTrackChangedListener {
        override fun onTrackChanged(track: MusicTrack) {
            // Обновление данных в вашей нижней панели
            // Например:
            nowPlayingTitleTextView.text = track.title
            nowPlayingArtistTextView.text = track.artist
            nowPlayingCoverImageView.setImageResource(track.imageResourse ?: 0)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        nowPlayingPanel = findViewById(R.id.now_playing_panel)

        setupRecyclerView()

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
        nowPlayingPanel = findViewById(R.id.now_playing_panel)
        nowPlayingTitleTextView = findViewById(R.id.nowPlayingTitleTextView)
        nowPlayingArtistTextView = findViewById(R.id.nowPlayingArtistTextView)
        nowPlayingCoverImageView = findViewById(R.id.nowPlayingCoverImageView)

        mediaPlayerManager.getCurrentTrack()?.let { yourListener.onTrackChanged(it) }

        // Добавление слушателя
        mediaPlayerManager.addOnTrackChangedListener(yourListener)

    }


    private fun setupRecyclerView() {
        val adapter = PlaylistAdapter(playlists) { playlist -> openPlaylistActivity(playlist) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

/*    private fun addPlaylistItem(playlist: Playlist) {
        val inflater = LayoutInflater.from(this)
        val playlistItemView = inflater.inflate(R.layout.one_item_from_playlisy, null)

        // Find ImageView and TextView in the playlist_item layout
        val playlistImageView: ImageView = playlistItemView.findViewById(R.id.playlistImageView)
        val playlistTitleTextView: TextView = playlistItemView.findViewById(R.id.playlistTitleTextView)

        // Set playlist image and title
        playlistImageView.setBackgroundResource(playlist.coverImageUrl)
        playlistTitleTextView.text = playlist.name

        // Set a click listener for the playlist item
        playlistItemView.setOnClickListener {
            openPlaylistActivity(playlist)
        }

        // Add the playlist item to the ConstraintLayout
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        playlistItemView.layoutParams = layoutParams
        constraintLayout.addView(playlistItemView)
    }*/

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
            Playlist("Мелодии для сна",  R.drawable.playlist_for_sleeping, tracks = getSampleTracksForPlaylistSleeping()),
            Playlist("Для хорошего настроения",  R.drawable.playlist_for_goodmood, tracks = getSampleTracksForPlaylistGoodMood()),
            Playlist("Когда грустно",  R.drawable.playlist_when_ur_sad, tracks = getSampleTracksForPlaylistSad()),
            Playlist("Классика",  R.drawable.playlist_for_classic, tracks = getSampleTracksForPlaylistClassic()),
        )
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayerManager.removeOnTrackChangedListener(yourListener)
    }
}
