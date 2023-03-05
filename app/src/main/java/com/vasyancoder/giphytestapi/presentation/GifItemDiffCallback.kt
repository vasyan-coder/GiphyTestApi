package com.vasyancoder.giphytestapi.presentation

import androidx.recyclerview.widget.DiffUtil
import com.vasyancoder.giphytestapi.domain.entities.GifItemEntity

class GifItemDiffCallback: DiffUtil.ItemCallback<GifItemEntity>() {
    override fun areItemsTheSame(oldItem: GifItemEntity, newItem: GifItemEntity): Boolean {
        return oldItem.gif.url == newItem.gif.url
    }

    override fun areContentsTheSame(oldItem: GifItemEntity, newItem: GifItemEntity): Boolean {
        return oldItem == newItem
    }
}