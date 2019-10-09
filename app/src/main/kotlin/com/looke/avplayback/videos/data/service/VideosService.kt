package com.looke.avplayback.videos.data.service

import com.looke.avplayback.videos.data.model.LookeVideosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface VideosService {

    @GET("/v0/b/desafio-dev-android.appspot.com/o/assets.json")
    suspend fun fetchVideos(
        @Query("alt") alt: String,
        @Query("token") token: String
    ): Response<LookeVideosResponse>
}
