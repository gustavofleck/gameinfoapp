package com.gustavo.architectureapp.data.model

import com.google.gson.annotations.SerializedName

data class GameResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("background_image") val backgroundImageUri: String?,
)