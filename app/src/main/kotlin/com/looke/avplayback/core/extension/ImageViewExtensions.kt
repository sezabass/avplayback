package com.looke.avplayback.core.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.api.load
import coil.transform.CircleCropTransformation

fun ImageView.loadImage(
    uri: String, @DrawableRes placeHolderDrawable: Int = 0,
    round: Boolean = false
) {
    this.load(uri) {
        if (placeHolderDrawable != 0) {
            placeholder(placeHolderDrawable)
        }
        if (round) {
            transformations(CircleCropTransformation())
        }
    }
}
