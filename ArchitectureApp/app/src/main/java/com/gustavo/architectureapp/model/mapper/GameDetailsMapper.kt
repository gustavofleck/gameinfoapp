package com.gustavo.architectureapp.model.mapper

import com.gustavo.architectureapp.model.games.GameDetails
import com.gustavo.architectureapp.model.games.GameDetailsResponse

class GameDetailsMapper {

    fun mapGameDetailsResponseToGameDetails(gameDetailsResponse: GameDetailsResponse): GameDetails {
        return GameDetails(
            gameDetailsResponse.name ?: "",
            gameDetailsResponse.description ?: "",
            gameDetailsResponse.backgroundImageUri ?: "",
            formatReleasedDate(gameDetailsResponse.released ?: ""),
            gameDetailsResponse.metacritic ?: 0
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