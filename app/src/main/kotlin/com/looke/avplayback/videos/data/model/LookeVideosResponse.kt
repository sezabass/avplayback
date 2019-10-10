package com.looke.avplayback.videos.data.model

import android.net.Uri
import com.looke.avplayback.videos.domain.LookeVideo
import com.looke.avplayback.videos.domain.LookeVideos

data class LookeVideosResponse(
    val objects: List<LookeVideoResponse>
) {

    fun toLookeVideos(): LookeVideos = LookeVideos(
        objects.map { it.toLookeVideo() }
    )
}

data class LookeVideoResponse(
    val name: String,
    val bg: String,
    val im: String,
    val sg: String
) {
    fun toLookeVideo(): LookeVideo = LookeVideo(
        name = name,
        videoURL = Uri.parse(bg),
        imageURL = im,
        audioURL = Uri.parse(sg)
    )
}

