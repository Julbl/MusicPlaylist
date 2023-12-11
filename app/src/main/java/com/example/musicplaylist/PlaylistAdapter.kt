package com.example.musicplaylist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.musicplaylist.Playlist
import java.util.Locale

class PlaylistAdapter(private val context: Context, private var playlists: List<Playlist>) : BaseAdapter(),
    Filterable {
    private var filteredPlaylists: List<Playlist> = playlists
    override fun getCount(): Int {
        return playlists.size
    }

    override fun getItem(position: Int): Any {
        return playlists[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.playlist_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val playlist = getItem(position) as Playlist

        viewHolder.nameTextView.text = playlist.name

        // Установка изображения плейлиста
        //viewHolder.imageView.setImageResource(playlist.coverImageUrl)

        return view
    }

    // Метод для обновления данных адаптера
    fun updateData(newPlaylists: List<Playlist>) {
        playlists = newPlaylists
        filteredPlaylists = playlists
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredResults = FilterResults()
                val query = constraint?.toString()?.toLowerCase(Locale.getDefault())

                val filteredList = if (query.isNullOrBlank()) {
                    playlists
                } else {
                    val typedList = playlists as? List<Playlist>
                    typedList?.filter {
                        it.name.toLowerCase(Locale.getDefault()).contains(query)
                    } ?: emptyList()
                }

                filteredResults.values = filteredList
                filteredResults.count = filteredList.size
                return filteredResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredPlaylists = results?.values as? List<Playlist> ?: emptyList<Playlist>()
                notifyDataSetChanged()
            }

        }
    }

    // ViewHolder для оптимизации производительности
    private class ViewHolder(view: View) {
        val nameTextView: TextView = view.findViewById(R.id.albumTitleTextView)
        //val imageView: ImageView = view.findViewById(R.id.playlistImageView)
    }
}
