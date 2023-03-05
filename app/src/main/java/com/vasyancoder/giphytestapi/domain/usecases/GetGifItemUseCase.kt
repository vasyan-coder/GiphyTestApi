package com.vasyancoder.giphytestapi.domain.usecases

import com.vasyancoder.giphytestapi.domain.model.GifItemModel
import com.vasyancoder.giphytestapi.domain.repository.GifRepository

class GetGifItemUseCase(
    private val repository: GifRepository
) {

    operator fun invoke(url: String): GifItemModel {
        return repository.getGifItem(url)
    }
}