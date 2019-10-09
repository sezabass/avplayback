package com.looke.avplayback.videos.data.repository

import com.looke.avplayback.core.domain.ResponseState
import com.looke.avplayback.core.domain.toResponseState
import com.looke.avplayback.videos.data.service.VideosService
import com.looke.avplayback.videos.domain.LookeVideos
import javax.inject.Inject

internal class VideosRepositoryImpl @Inject constructor(
    private val service: VideosService
) : VideosRepository {
    override suspend fun fetchVideos(): ResponseState<LookeVideos> {
        val alt = "media"
        val token = "964a35bb-53d0-45aa-a3dd-ecad72a2f14c"
        return service.fetchVideos(alt, token).toResponseState().map { it.toLookeVideos() }
    }
}
