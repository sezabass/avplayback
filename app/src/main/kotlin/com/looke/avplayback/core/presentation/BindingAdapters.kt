package com.looke.avplayback.core.presentation

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.looke.avplayback.core.extension.loadImage

object BindingAdapters {
    @BindingAdapter(value = ["imageUrl", "round", "placeholderDrawable"], requireAll = false)
    @JvmStatic
    fun loadImageUrl(
        imageView: ImageView,
        url: String?,
        round: Boolean?,
        @DrawableRes placeholder: Int?
    ) {
        url?.let {
            imageView.loadImage(
                uri = it,
                round = round ?: false,
                placeHolderDrawable = placeholder ?: 0
            )
        }
    }
}
