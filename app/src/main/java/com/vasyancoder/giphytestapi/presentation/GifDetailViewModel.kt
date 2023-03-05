package com.vasyancoder.giphytestapi.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.vasyancoder.giphytestapi.data.config.OkHttpConfig
import com.vasyancoder.giphytestapi.data.reposirory.GifRepositoryImpl
import com.vasyancoder.giphytestapi.domain.entities.GifDetailEntity
import com.vasyancoder.giphytestapi.domain.entities.GifItemEntity
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class GifDetailViewModel : ViewModel() {
    private val repository: GifRepositoryImpl

    private val _gifDetail = MutableLiveData<GifDetailEntity>()
    val gifDetail: LiveData<GifDetailEntity>
        get() = _gifDetail

    init {
        val client = OkHttpClient.Builder()
            .build()
        val gson = Gson()
        val config = OkHttpConfig(
            baseUrl = URL,
            client = client,
            gson = gson
        )
        repository = GifRepositoryImpl(config)
    }

    fun getGifDetail(position: Int) {
        viewModelScope.launch {
            _gifDetail.value = repository.getGifDetail(position)
        }
    }

    companion object {
        private const val URL = "https://api.giphy.com/v1/gifs/trending?api_key="
    }
}