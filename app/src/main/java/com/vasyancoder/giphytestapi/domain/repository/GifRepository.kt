package com.vasyancoder.giphytestapi.domain.repository

import com.vasyancoder.giphytestapi.domain.entities.GifItemEntity

interface GifRepository {

        suspend fun getGifList() : List<GifItemEntity>
}