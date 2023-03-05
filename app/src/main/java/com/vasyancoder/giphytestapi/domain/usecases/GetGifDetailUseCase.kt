package com.vasyancoder.giphytestapi.domain.usecases

import com.vasyancoder.giphytestapi.domain.entities.GifDetailEntity
import com.vasyancoder.giphytestapi.domain.entities.GifItemEntity
import com.vasyancoder.giphytestapi.domain.repository.GifRepository

class GetGifDetailUseCase(
    private val repository: GifRepository
) {

    suspend operator fun invoke(position: Int): GifDetailEntity {
        return repository.getGifDetail(position)
    }
}