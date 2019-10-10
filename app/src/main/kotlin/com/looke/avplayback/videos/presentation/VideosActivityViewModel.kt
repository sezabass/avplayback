package com.looke.avplayback.videos.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.looke.avplayback.core.domain.ResponseState
import com.looke.avplayback.core.domain.ScreenState
import com.looke.avplayback.core.livedata.SingleLiveEvent
import com.looke.avplayback.videos.data.repository.VideosRepository
import com.looke.avplayback.videos.domain.LookeVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class VideosActivityViewModel @Inject constructor(
    private val repository: VideosRepository
) : ViewModel() {

    val videosListState = SingleLiveEvent<ScreenState<List<LookeVideo>>>()
    val openVideo = SingleLiveEvent<LookeVideo>()
    val isLoading: Boolean
        get() = videosListState.value?.let { it is ScreenState.Loading } ?: false

    fun fetchVideos() {

        viewModelScope.launch {
            videosListState.postValue(ScreenState.Loading)

            val response = withContext(Dispatchers.IO) {
                repository.fetchVideos()
            }

            when (response) {
                is ResponseState.Success -> videosListState.postValue(
                    ScreenState.Success(response.data.objects)
                )
                is ResponseState.ServerError -> videosListState.postValue(
                    ScreenState.Error(response.message)
                )
                is ResponseState.GeneralError -> videosListState.postValue(
                    ScreenState.Error(response.e.message)
                )
            }
        }
    }

    fun onUserRequestedToOpenVideo(index: Int) {
        val videosList = videosListState.value!! as ScreenState.Success<List<LookeVideo>>
        val selectedVideo = videosList.data[index]
        openVideo.postValue(selectedVideo)
    }

}

