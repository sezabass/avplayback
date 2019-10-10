package com.looke.avplayback.core.presentation

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.looke.avplayback.core.extension.hide
import com.looke.avplayback.core.extension.loadImage
import com.looke.avplayback.core.extension.show

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

    @BindingAdapter("visible")
    @JvmStatic
    fun visible(
        view: View,
        show: Boolean
    ) {
        if (show) view.show() else view.hide()
    }
}
