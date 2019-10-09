package com.looke.avplayback.videos.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LookeVideos(
    val objects: List<LookeVideo>
): Parcelable

@Parcelize
data class LookeVideo(
    val name: String,
    val videoURL: String,
    val imageURL: String,
    val audioURL: String
): Parcelable
