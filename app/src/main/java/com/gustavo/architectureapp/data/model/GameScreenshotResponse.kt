package com.gustavo.architectureapp.data.model

import com.google.gson.annotations.SerializedName

data class GameScreenshotResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val imageUri: String
)