package com.vasyancoder.giphytestapi.domain.model

data class GifItemModel(
    val imageUrl: String,
    val downloaded: Boolean = false
)