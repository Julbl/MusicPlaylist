package com.example.musicplaylist


data class MusicTrack(
    val title: String,
    val artist: String,
    val album: String,
    val imageResourse: Int,
)

fun getSampleTracksForPlaylistGoodMood(): List<MusicTrack> {
    return listOf(
        MusicTrack("Asphalt 8", "MACAN", "Для хорошего настроения", R.raw.asphalt),

        // Добавьте другие треки, если необходимо
    )
}