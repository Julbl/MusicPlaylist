package com.example.musicplaylist

import android.content.Context
import android.media.MediaPlayer
import android.widget.Toast

class MediaPlayerManager private constructor(){
    private var mediaPlayer: MediaPlayer? = null
    private var currentTrack: MusicTrack? = null

    interface OnTrackChangedListener {
        fun onTrackChanged(track: MusicTrack)
    }

    private val trackChangedListeners = mutableListOf<OnTrackChangedListener>()

    fun addOnTrackChangedListener(listener: OnTrackChangedListener) {
        trackChangedListeners.add(listener)
    }

    fun removeOnTrackChangedListener(listener: OnTrackChangedListener) {
        trackChangedListeners.remove(listener)
    }

    private fun notifyTrackChanged(track: MusicTrack) {
        for (listener in trackChangedListeners) {
            listener.onTrackChanged(track)
        }
    }

    companion object {
        @Volatile
        private var instance: MediaPlayerManager? = null

        fun getInstance(): MediaPlayerManager =
            instance ?: synchronized(this) {
                instance ?: MediaPlayerManager().also { instance = it }
            }
    }

    fun playTrack(context: Context, track: MusicTrack) {
        // Останавливаем предыдущий трек, если он существует
        stopTrack()
        mediaPlayer?.release()

        // Получаем ресурс для аудиофайла
        val audioResId = context.resources.getIdentifier(track.audioFileName, "raw", context.packageName)

        // Проверяем, что ресурс существует
        if (audioResId != 0) {
            // Создаем новый mediaPlayer для выбранного трека и воспроизводим его
            mediaPlayer = MediaPlayer.create(context, audioResId)
            mediaPlayer?.start()

            // Сохраняем текущий трек
            currentTrack = track

            // Обновляем интерфейс
            PanelManager.updateTrack(track)
        } else {
            // Ресурс не найден, выводим сообщение об ошибке или что-то подобное
            Toast.makeText(context, "Resource not found for track: ${track.audioFileName}", Toast.LENGTH_SHORT).show()
        }
    }
    fun stopTrack() {
        // Останавливаем предыдущий трек, если он существует
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        // Сбрасываем текущий трек
        currentTrack = null
        mediaPlayer = null
    }

    fun getCurrentTrack(): MusicTrack? {
        return currentTrack
    }

    // Другие методы управления воспроизведением, если нужно
}
