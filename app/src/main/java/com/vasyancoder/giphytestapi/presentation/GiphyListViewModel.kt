package com.vasyancoder.giphytestapi.presentation

import android.util.Log
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
        val client: OkHttpClient = OkHttpClient.Builder()
            .build()
        val gson = Gson()
        val config = OkHttpConfig(
            baseUrl = BASE_URL,
            client = client,
            gson = gson
        )
        repository = GifRepositoryImpl(config)
    }

    private val _gifList = MutableLiveData<List<GifItemEntity>>()
    val gifList: LiveData<List<GifItemEntity>>
        get() = _gifList

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String>
        get() = _searchQuery

    fun getGifItemList() {
        if (searchQuery.value == "" || searchQuery.value == null) {
            viewModelScope.launch {
                _gifList.postValue(repository.getGifList(TREND_URL, searchQuery.value ?: ""))
            }
        } else {
            viewModelScope.launch {
                _gifList.postValue(repository.getGifList(SEARCH_URL, searchQuery.value ?: ""))
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    companion object {
        private const val TREND_URL = "trending?api_key="
        private const val SEARCH_URL = "search?api_key="
        private const val BASE_URL = "https://api.giphy.com/v1/gifs/"
    }

}