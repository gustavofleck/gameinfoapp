package com.gustavo.architectureapp.data.games

import com.google.gson.annotations.SerializedName

data class GamePlatformResponse (
        @SerializedName("platform") val platform: PlatformResponse?,

        @SerializedName("released_at") val releasedAt: String?,

        @SerializedName("requirements") val requirements: RequirementsResponse?
)