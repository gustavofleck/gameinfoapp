package com.gustavo.architectureapp.data.model

import com.google.gson.annotations.SerializedName

data class GameListingResponse (
   @SerializedName("next") val nextPage: String?,
   @SerializedName("results") val gameList: List<GameResponse>?
)