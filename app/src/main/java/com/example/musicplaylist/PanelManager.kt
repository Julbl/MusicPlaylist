package com.example.musicplaylist

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import java.lang.ref.WeakReference

object PanelManager {
    private var track: MusicTrack? = null
    private var listener: ((MusicTrack?) -> Unit)? = null
    private var nowPlayingPlayButtonRef: WeakReference<Button>? = null
    private var nowPlayingButtonClickListenerRef: WeakReference<View.OnClickListener>? = null

    fun setNowPlayingPlayButton(button: Button) {
        nowPlayingPlayButtonRef = WeakReference(button)
    }

    fun setListener(listener: (MusicTrack?) -> Unit) {
        this.listener = listener
        listener.invoke(track)
    }

    fun updateTrack(track: MusicTrack) {
        this.track = track
        listener?.invoke(track)
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        // Создаем слабую ссылку на обработчик кликов
        nowPlayingButtonClickListenerRef = WeakReference(listener)

        // Получаем сильную ссылку на кнопку Play
        val playButton = nowPlayingPlayButtonRef?.get()

        // Проверяем, что кнопка Play существует и устанавливаем слушатель кликов
        playButton?.setOnClickListener(listener)
    }
    fun onPlayButtonClicked(context: Context) {
        // Получаем сильную ссылку на текущий трек
        val currentTrack = track

        // Проверяем, что текущий трек существует
        if (currentTrack != null) {
            // Создаем Intent для открытия NowPlayingActivity
            val intent = Intent(context, NowPlayingActivity::class.java)

            // Передаем данные текущего трека в NowPlayingActivity через Intent
            intent.putExtra("track", currentTrack)

            // Запускаем активность
            context.startActivity(intent)
        } else {
            // Сообщение об ошибке или что-то подобное, если трек не существует
            Log.e("PanelManager", "Current track is null")
            Toast.makeText(context, "Current track is null", Toast.LENGTH_SHORT).show()
        }
    }

}
