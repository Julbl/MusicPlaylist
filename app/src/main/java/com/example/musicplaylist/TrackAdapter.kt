package com.example.musicplaylist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.Locale

class TrackAdapter(private val context: Context, private val allTracks: List<MusicTrack>) :
    RecyclerView.Adapter<TrackAdapter.TrackViewHolder>(), Filterable {
    private var filteredTracks: List<MusicTrack> = allTracks
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.track_item, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = filteredTracks[position]
        holder.bind(track)
    }

    override fun getItemCount(): Int {
        return filteredTracks.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.toLowerCase(Locale.getDefault())

                filteredTracks = if (query.isNullOrBlank()) {
                    allTracks
                } else {
                    allTracks.filter {
                        it.title.toLowerCase(Locale.getDefault()).contains(query) ||
                                it.artist.toLowerCase(Locale.getDefault()).contains(query)
                    }
                }

                val results = FilterResults()
                results.values = filteredTracks
                results.count = filteredTracks.size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null) {
                    filteredTracks = results.values as? List<MusicTrack> ?: emptyList()
                    notifyDataSetChanged()
                }
            }
        }
    }
    inner class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.trackTitleTextView)
        private val artistTextView: TextView = itemView.findViewById(R.id.trackArtistTextView)
        private val albumTextView: TextView = itemView.findViewById(R.id.trackAlbumTextView)

        fun bind(track: MusicTrack) {
            titleTextView.text = track.title
            artistTextView.text = track.artist
            albumTextView.text = track.album
            // Другие детали, если есть
        }
    }
}

