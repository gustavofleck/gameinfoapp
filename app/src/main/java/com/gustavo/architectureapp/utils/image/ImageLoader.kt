package com.gustavo.architectureapp.utils.image

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.gustavo.architectureapp.R
import com.gustavo.architectureapp.utils.builder.CircularProgressDrawableBuilder

class ImageLoader(
    private val context: Context // Teste com robolectric
) {
    private var placeholder = CircularProgressDrawableBuilder(context).build()
    private var errorImageId = R.mipmap.ic_launcher

    fun setPlaceholder(placeholder: CircularProgressDrawable) : ImageLoader {
        this.placeholder = placeholder
        return this
    }

    fun setError(errorImageId: Int) : ImageLoader {
        this.errorImageId = errorImageId
        return this
    }

    fun loadImage(imageView: ImageView, imageUri: String) {

        Glide.with(context)
            .load(imageUri)
            .placeholder(placeholder)
            .error(errorImageId)
            .circleCrop()
            .into(imageView)

    }
}