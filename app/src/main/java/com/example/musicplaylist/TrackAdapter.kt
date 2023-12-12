package com.example.musicplaylist

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class TrackAdapter(private val context: Context, private val allTracks: List<MusicTrack>) :
    RecyclerView.Adapter<TrackAdapter.TrackViewHolder>(), Filterable {
    private var filteredTracks: List<MusicTrack> = allTracks
    private val mediaPlayerManager = MediaPlayerManager.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.track_item, parent, false)
        return TrackViewHolder(view)

    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = filteredTracks[position]
        holder.bind(track)
        holder.itemView.setOnClickListener {
            mediaPlayerManager.playTrack(context, track)
            PanelManager.updateTrack(track)
            openNowPlayingActivity(track)
        }
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


    private fun openNowPlayingActivity(track: MusicTrack) {
        Log.i("TrackAdapter", "Opening NowPlayingActivity for track: ${track.title}")
        val intent = Intent(context, NowPlayingActivity::class.java)
        intent.putExtra("track", track)
        context.startActivity(intent)
    }
    inner class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.trackTitleTextView)
        private val artistTextView: TextView = itemView.findViewById(R.id.trackArtistTextView)
        private val albumTextView: TextView = itemView.findViewById(R.id.trackAlbumTextView)
        private val albumImageView: ImageView = itemView.findViewById(R.id.albumImageView)
        fun bind(track: MusicTrack) {
            titleTextView.text = track.title
            artistTextView.text = track.artist
            albumTextView.text = track.album
            albumImageView.setImageResource(track.imageResourse)
            // Другие детали, если есть
        }
    }
}

