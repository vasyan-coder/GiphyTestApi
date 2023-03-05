package com.vasyancoder.giphytestapi.data.reposirory


import android.util.Log
import com.vasyancoder.giphytestapi.data.config.BaseOkHttpSource
import com.vasyancoder.giphytestapi.data.config.OkHttpConfig
import com.vasyancoder.giphytestapi.domain.entities.GifItemEntity
import com.vasyancoder.giphytestapi.domain.entities.ImageEntity
import com.vasyancoder.giphytestapi.domain.repository.GifRepository
import okhttp3.Request

class GifRepositoryImpl(
    config: OkHttpConfig
) : BaseOkHttpSource(config), GifRepository {

    override suspend fun getGifList(): List<GifItemEntity> {
        val request = Request.Builder()
            .get()
            .endpoint(API_KEY)
            .build()

        val response = client.newCall(request).suspendEnqueue()
        val listImageEntities = response.parseJsonResponse<ImageEntity>()
        val listGifItemEntities = mutableListOf<GifItemEntity>()
        for (imageEntity in listImageEntities) {
            listGifItemEntities.add(
                GifItemEntity(
                    imageEntity
                )
            )
        }
        return listGifItemEntities
    }

    companion object {

        private const val API_KEY = "cT53te0AadxEybUZFHQCt8u5u87QtxO1"
    }
}