package com.gustavo.architectureapp.data.games

import com.google.gson.annotations.SerializedName

data class PlatformResponse (
    @SerializedName("id") val id: Int,

    @SerializedName("name") val name: String
)
