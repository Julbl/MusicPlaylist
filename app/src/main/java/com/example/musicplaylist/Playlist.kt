package com.example.musicplaylist
import java.io.Serializable

data class Playlist (
    val name: String,
    val coverImageUrl: Int,
    val tracks: List<MusicTrack>
):Serializable
