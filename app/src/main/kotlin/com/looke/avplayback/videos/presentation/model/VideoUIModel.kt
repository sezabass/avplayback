package com.looke.avplayback.videos.presentation.model

import com.looke.avplayback.R
import com.looke.avplayback.videos.domain.LookeVideo

internal class VideoUIModel(
    video: LookeVideo
) {
    val name = video.name
    val videoURL = video.videoURL
    val imageURL = video.imageURL
    val audioURL = video.audioURL

    val defaultDrawable = R.drawable.ic_ondemand_video_black_48dp
}
