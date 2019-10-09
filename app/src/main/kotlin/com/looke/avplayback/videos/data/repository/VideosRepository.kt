package com.looke.avplayback.videos.data.repository

import com.looke.avplayback.core.domain.ResponseState
import com.looke.avplayback.videos.domain.LookeVideos

internal interface VideosRepository {
    suspend fun fetchVideos(): ResponseState<LookeVideos>
}
