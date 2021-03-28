package com.gustavo.architectureapp.utils.builder

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

class CircularProgressDrawableBuilder(private val context: Context) {

    private var strokewidth = 5f
    private var centerRadius = 50f

    fun setStrokeWidth(value: Float) : CircularProgressDrawableBuilder {
        strokewidth = value
        return this
    }

    fun setCenterRadius(value: Float) : CircularProgressDrawableBuilder {
        centerRadius = value
        return this
    }

    fun build() : CircularProgressDrawable {
        return CircularProgressDrawable(context).apply {
            strokeWidth = this@CircularProgressDrawableBuilder.strokewidth
            centerRadius = this@CircularProgressDrawableBuilder.centerRadius
        }
    }

}