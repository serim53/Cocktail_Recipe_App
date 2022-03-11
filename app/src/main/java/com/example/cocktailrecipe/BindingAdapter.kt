package com.example.cocktailrecipe

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    // JvmStatic -> 외부 접근 가능하도록 설정
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun load(imageView: ImageView, url: String?) {
        if (url.isNullOrEmpty().not()) {
            Glide.with(imageView.context).load(url).into(imageView)
        }
    }
}