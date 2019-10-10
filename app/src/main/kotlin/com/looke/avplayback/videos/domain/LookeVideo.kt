package com.looke.avplayback.videos.domain

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LookeVideos(
    val objects: List<LookeVideo>
) : Parcelable

@Parcelize
data class LookeVideo(
    val name: String,
    val videoURL: Uri,
    val imageURL: String,
    val audioURL: Uri
) : Parcelable
