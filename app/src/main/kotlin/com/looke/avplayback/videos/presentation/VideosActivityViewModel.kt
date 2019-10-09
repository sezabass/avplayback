package com.looke.avplayback.videos.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.looke.avplayback.core.domain.ResponseState
import com.looke.avplayback.videos.data.repository.VideosRepository
import com.looke.avplayback.videos.domain.LookeVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class VideosActivityViewModel @Inject constructor(
    private val repository: VideosRepository
) : ViewModel() {

    val videos = MutableLiveData<List<LookeVideo>>()

    fun fetchVideos() {

        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.fetchVideos()
            }

            when (response) {
                is ResponseState.Success -> {
                    Log.d("ViewModel", "Quantidade de vídeos: " + response.data.objects.size)
                    videos.postValue(response.data.objects)
                }
                is ResponseState.ServerError -> Log.d("ViewModel", response.message)
                is ResponseState.GeneralError -> Log.d("ViewModel", response.e.message ?: "general error")
            }
        }
    }
}
