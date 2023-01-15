package com.demo.ui.albums.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.demo.R
import com.demo.domain.model.albums.Album


class AlbumAdapter(private val listener: AlbumAdapterInterface) : RecyclerView.Adapter<AlbumAdapter.RecyclerViewHolder>() {

    private val data = mutableListOf<ModelAlbum>()

    fun setDataList(newData: List<ModelAlbum>) {
        data.clear()
        data.addAll(newData)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_album_item, parent, false)
            .let { RecyclerViewHolder(it) }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val data = data[position]

        holder.albumID.text = holder.itemView.context.resources.getString(R.string.album_id, data.album.id)
        holder.userID.text = holder.itemView.context.resources.getString(R.string.album_user_id, data.album.userId)
        holder.albumName.text = holder.itemView.context.resources.getString(R.string.album_name, data.album.title)
        holder.intermediary.isVisible = data.intermediary

        holder.itemView.setOnClickListener { listener.modelAlbumSelected(data) }
    }

    override fun getItemCount(): Int = data.size

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val intermediary: TextView = itemView.findViewById(R.id.albumIntermediary)
        val albumID: TextView = itemView.findViewById(R.id.albumIdLabel)
        val userID: TextView = itemView.findViewById(R.id.albumUserIdLabel)
        val albumName: TextView = itemView.findViewById(R.id.albumNameLabel)
    }
}

/**
 * Model [Album] used to allow [intermediary] checks to happen in a single place.
 *
 * Used specifically by [AlbumAdapter] and [FragmentAlbumList] to determiner potential intermediary steps.
 */
data class ModelAlbum(
    val album: Album,
    val intermediary: Boolean = album.id % 5 == 0
)

interface AlbumAdapterInterface {
    fun modelAlbumSelected(modelAlbum: ModelAlbum)
}