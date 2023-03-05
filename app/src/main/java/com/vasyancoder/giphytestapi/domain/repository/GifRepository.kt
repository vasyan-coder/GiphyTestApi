package com.vasyancoder.giphytestapi.domain.repository

import com.vasyancoder.giphytestapi.domain.model.GifItemModel

interface GifRepository {

    fun getGifItem(
        url: String
    ): GifItemModel
}