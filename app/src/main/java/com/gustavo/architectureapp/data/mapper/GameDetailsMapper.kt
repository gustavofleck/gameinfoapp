package com.gustavo.architectureapp.data.mapper

import android.net.Uri
import com.gustavo.architectureapp.data.model.*

class GameDetailsMapper {

    fun mapGameScreenshotListToGameImages(gameScreenshotListResponse: GameScreenshotListResponse): GameImages {
        return GameImages(
            mapNextPage(gameScreenshotListResponse.nextPage ?: ""),
            gameScreenshotListResponse.gameScreenshotList.map { gameScreenshotList ->
                GameScreenshot(
                    gameScreenshotList.imageUri
                )
            }
        )
    }

    fun mapGameDetailsResponseToGameDetails(gameDetailsResponse: GameDetailsResponse): GameDetails {
        return GameDetails(
            gameDetailsResponse.name ?: "",
            gameDetailsResponse.description ?: "",
            gameDetailsResponse.backgroundImageUri ?: "",
            formatReleasedDate(gameDetailsResponse.released ?: ""),
            gameDetailsResponse.metacritic ?: "",
            gameDetailsResponse.website ?: ""
        )
    }

    private fun mapNextPage(nextPage: String): Int = try {
        val page = Uri.parse(nextPage).getQueryParameter("page") ?: "0"
        page.toInt()
    } catch (exception: Exception) {
        0
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