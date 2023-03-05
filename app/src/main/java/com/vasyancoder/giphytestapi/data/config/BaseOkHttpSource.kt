package com.vasyancoder.giphytestapi.data.config

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.vasyancoder.giphytestapi.data.exceptions.ConnectionException
import com.vasyancoder.giphytestapi.data.exceptions.ParseBackendResponseException
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
    inline fun <reified T> Response.parseJsonResponse(): List<T> {
        try {
            val list = mutableListOf<T>()
            val responseBodyString = this.body!!.string()
            val jsonObject: JsonObject = JsonParser.parseString(responseBodyString).asJsonObject
            val jsonData = jsonObject.get("data").asJsonArray
            for (jsonImages in jsonData) {
                val jsonImages = jsonImages.asJsonObject.get("images").asJsonObject
                val jsonGif = jsonImages.get("downsized_medium")
                list.add(gson.fromJson(jsonGif, T::class.java))
            }
            return list

        } catch (e: Exception) {
            throw ParseBackendResponseException(e)
        }
    }
}