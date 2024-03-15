package com.jnasser.movieapp.ui.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jnasser.movieapp.framework.requestmanager.APIConstants

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        url?.let {
            Glide.with(view.context)
                .load(APIConstants.IMAGE_BASE_URL + it)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view)
        }
    }

}