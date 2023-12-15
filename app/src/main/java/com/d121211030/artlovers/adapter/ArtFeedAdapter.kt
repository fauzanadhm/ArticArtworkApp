package com.d121211030.artlovers.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.d121211030.artlovers.R
import com.d121211030.artlovers.data.model.Artwork

class ArtFeedAdapter(
    val lovePressedCallback: (Artwork) -> Unit,
    val detailPressedCallback: (Artwork) -> Unit,
) : RecyclerView.Adapter<ArtworkViewHolder>() {
    private val artworkList: MutableList<Artwork> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtworkViewHolder {
        return ArtworkViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.artwork_feed_item, parent,false)
        )
    }

    override fun getItemCount(): Int {
        return artworkList.size
    }

    override fun onBindViewHolder(holder: ArtworkViewHolder, position: Int) {
        if (itemCount < 1) {
            return
        }
        val artwork = artworkList[position]
        val lovedButton = holder.itemView.findViewById<ImageView>(R.id.loved_button)

        with(holder.itemView) {
            findViewById<TextView>(R.id.title).text = artwork.title
            findViewById<TextView>(R.id.artist).text = artwork.artistDisplay
            setLoveImageView(artwork.isLoved, lovedButton)
        }

        lovedButton.setOnClickListener {
            lovePressedCallback(artwork)
        }

        holder.itemView.setOnClickListener {
            detailPressedCallback(artwork)
        }
    }

    private fun setLoveImageView(isLoved: Boolean?, imageView: ImageView) {
        if (isLoved == true) {
            imageView.setImageResource(R.drawable.ic_heart)
        } else {
            imageView.setImageResource(R.drawable.ic_heart_outline)
        }
    }

    //TODO instead of replacing entire list, update only changed items
    fun updateListItems(listItems: List<Artwork>) {
        if (artworkList != listItems) {
//            Log.i("ArtFeedAdapter", "Adding ${listItems.size} items")
            val previousSize = itemCount
            artworkList.clear()
            notifyItemRangeRemoved(0, previousSize)
            artworkList.addAll(listItems)
            notifyItemRangeInserted(0, listItems.size)
        }
    }
}

class ArtworkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
