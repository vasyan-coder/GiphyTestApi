package com.vasyancoder.giphytestapi.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vasyancoder.giphytestapi.R
import com.vasyancoder.giphytestapi.domain.entities.GifItemEntity

class GifListAdapter
    : ListAdapter<GifItemEntity, GifListAdapter.GifListViewHolder>(GifItemDiffCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gif, parent, false)
        return GifListViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifListViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(getItem(position).gif.url)
            .into(holder.gifImage)
    }

    class GifListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gifImage: ImageView = view.findViewById(R.id.gifImageView)
    }
}