package com.vasyancoder.giphytestapi.domain.usecases

import com.vasyancoder.giphytestapi.domain.entities.GifItemEntity
import com.vasyancoder.giphytestapi.domain.repository.GifRepository

class GetGifItemListUseCase(
    private val repository: GifRepository
) {

    suspend operator fun invoke(): List<GifItemEntity> {
        return repository.getGifList()
    }
}