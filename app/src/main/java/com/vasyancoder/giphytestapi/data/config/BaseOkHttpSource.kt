package com.vasyancoder.giphytestapi.data.config

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.vasyancoder.giphytestapi.data.exceptions.ConnectionException
import com.vasyancoder.giphytestapi.data.exceptions.ParseBackendResponseException
import com.vasyancoder.giphytestapi.domain.entities.GifDetailEntity
import com.vasyancoder.giphytestapi.domain.entities.GifItemEntity
import com.vasyancoder.giphytestapi.domain.entities.ImageEntity
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

open class BaseOkHttpSource(
    private val config: OkHttpConfig
) {

    val gson: Gson = config.gson
    val client: OkHttpClient = config.client

    suspend fun Call.suspendEnqueue(): Response {
        return suspendCancellableCoroutine { continuation ->
            continuation.invokeOnCancellation {
                cancel()
            }
            enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val appException = ConnectionException(e)
                    continuation.resumeWithException(appException)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        continuation.resume(response)
                    }
                }
            })
        }
    }

    fun Request.Builder.endpoint(endpoint: String): Request.Builder {
        url("${config.baseUrl}$endpoint")
        return this
    }

    /**
     * Read Json objects, returned list
     */
    fun Response.parseJsonResponseGifs(): List<ImageEntity> {
        try {
            val list = mutableListOf<ImageEntity>()
            val responseBodyString = this.body!!.string()
            val jsonObject: JsonObject = JsonParser.parseString(responseBodyString).asJsonObject
            val jsonData = jsonObject.get("data").asJsonArray
            for (jsonImages in jsonData) {
                val jsonImages = jsonImages.asJsonObject.get("images").asJsonObject
                val jsonGif = jsonImages.get("downsized_medium")
                list.add(gson.fromJson(jsonGif, ImageEntity::class.java))
            }
            return list

        } catch (e: Exception) {
            throw ParseBackendResponseException(e)
        }
    }


    /**
     * Read Json objects, returned object
     */
    fun Response.parseJsonResponseGifDetail(position: Int): GifDetailEntity {
        try {
            val responseBodyString = this.body!!.string()
            val jsonObject: JsonObject = JsonParser.parseString(responseBodyString).asJsonObject
            val jsonData = jsonObject.get("data").asJsonArray
            val gif = jsonData[position].asJsonObject
            return GifDetailEntity(
                id = gif.get(KEY_ID).toString(),
                username = gif.get(KEY_USERNAME).toString(),
                title = gif.get(KEY_TITLE).toString(),
                import_datetime = gif.get(KEY_IMPORT_DATETIME).toString()
            )

        } catch (e: Exception) {
            throw ParseBackendResponseException(e)
        }
    }

    companion object {

        private const val KEY_ID = "id"
        private const val KEY_USERNAME = "username"
        private const val KEY_TITLE = "title"
        private const val KEY_IMPORT_DATETIME = "import_datetime"
    }
}