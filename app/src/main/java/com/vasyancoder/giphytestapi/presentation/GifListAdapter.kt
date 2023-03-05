package com.vasyancoder.giphytestapi.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vasyancoder.giphytestapi.R
import com.vasyancoder.giphytestapi.domain.entities.GifItemEntity

class GifListAdapter
    : ListAdapter<GifItemEntity, GifListAdapter.GifListViewHolder>(GifItemDiffCallback()) {

    var onGifItemClickListener: ((GifItemEntity, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gif, parent, false)
        return GifListViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifListViewHolder, position: Int) {
        val gifItem = getItem(position)
        Glide.with(holder.itemView.context)
            .load(gifItem.gif.url)
            .into(holder.gifImage)
        holder.cardView.setOnClickListener {
            onGifItemClickListener?.invoke(gifItem, position)
        }
    }

    class GifListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gifImage: ImageView = view.findViewById(R.id.gifImageView)
        val cardView: CardView = view.findViewById(R.id.cardView)
    }
}