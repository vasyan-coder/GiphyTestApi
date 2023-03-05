package com.vasyancoder.giphytestapi.domain.entities

data class GifItemEntity(
    val gif: ImageEntity,
    val downloaded: Boolean = true
)