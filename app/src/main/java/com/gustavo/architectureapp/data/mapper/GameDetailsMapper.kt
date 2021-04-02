package com.gustavo.architectureapp.data.mapper

import com.gustavo.architectureapp.data.games.GameDetails
import com.gustavo.architectureapp.data.games.GameDetailsResponse

class GameDetailsMapper {

    fun mapGameDetailsResponseToGameDetails(gameDetailsResponse: GameDetailsResponse): GameDetails {
        return GameDetails(
            gameDetailsResponse.name ?: "",
            gameDetailsResponse.description ?: "",
            gameDetailsResponse.backgroundImageUri ?: "",
            formatReleasedDate(gameDetailsResponse.released ?: ""),
            gameDetailsResponse.metacritic ?: ""
        )
    }

    private fun formatReleasedDate(releaseDate: String): String {
        return if (releaseDate.isNotEmpty()) {
            val dateArray = releaseDate.split("-")
            "${dateArray[2]}/${dateArray[1]}/${dateArray[0]}"
        } else {
            ""
        }
    }
}