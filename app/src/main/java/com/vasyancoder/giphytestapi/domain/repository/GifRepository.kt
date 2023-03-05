package com.vasyancoder.giphytestapi.domain.repository

import com.vasyancoder.giphytestapi.domain.entities.GifDetailEntity
import com.vasyancoder.giphytestapi.domain.entities.GifItemEntity

interface GifRepository {

    suspend fun getGifList(url_func: String = "", args: String = ""): List<GifItemEntity>

    suspend fun getGifDetail(position: Int): GifDetailEntity
}