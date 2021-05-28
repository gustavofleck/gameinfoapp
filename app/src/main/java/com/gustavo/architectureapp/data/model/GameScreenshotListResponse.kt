package com.gustavo.architectureapp.data.model

import com.google.gson.annotations.SerializedName

data class GameScreenshotListResponse(
    @SerializedName("next") val nextPage: String?,
    @SerializedName("results") val gameScreenshotList: List<GameScreenshotResponse>
)