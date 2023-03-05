package com.vasyancoder.giphytestapi.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.vasyancoder.giphytestapi.data.config.OkHttpConfig
import com.vasyancoder.giphytestapi.data.reposirory.GifRepositoryImpl
import com.vasyancoder.giphytestapi.domain.entities.GifItemEntity
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class GiphyListViewModel : ViewModel() {

    private val repository: GifRepositoryImpl

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

    private val _gifList = MutableLiveData<List<GifItemEntity>>()
    val gifList: LiveData<List<GifItemEntity>>
        get() = _gifList

    fun getGifItemList() {
        viewModelScope.launch {
            _gifList.postValue(repository.getGifList())
        }
    }

    companion object {
        private const val URL = "https://api.giphy.com/v1/gifs/trending?api_key="
    }

}