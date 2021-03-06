package com.gustavo.architectureapp.data.model

import com.google.gson.annotations.SerializedName

data class GameDetailsResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("description_raw") val description: String?,
    @SerializedName("background_image") val backgroundImageUri: String?,
    @SerializedName("released") val released: String?,
    @SerializedName("metacritic") val metacritic: String?,
    @SerializedName("website") val website: String?
)