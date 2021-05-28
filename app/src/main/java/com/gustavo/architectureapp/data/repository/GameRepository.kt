package com.gustavo.architectureapp.data.repository

import com.gustavo.architectureapp.data.api.GamesApiService
import com.gustavo.architectureapp.data.model.GameDetails
import com.gustavo.architectureapp.data.model.GameList
import com.gustavo.architectureapp.data.mapper.GameDetailsMapper
import com.gustavo.architectureapp.data.mapper.GameListingMapper
import com.gustavo.architectureapp.data.model.GameImages
import com.gustavo.architectureapp.data.model.GameScreenshotListResponse

class GameRepository(
    private val gamesApi: GamesApiService,
    private val gameListingMapper: GameListingMapper,
    private val gameDetailsMapper: GameDetailsMapper
) {

    suspend fun getAllGames(platformId: Int, nextPage: Int, searchQuery: String = ""): GameList {
        val gameListResponse =
            gamesApi.getGameList(
                platform = platformId,
                page = nextPage,
                search = searchQuery
            )
        return gameListingMapper.mapGameListingResponseToGameList(gameListResponse)
    }

    suspend fun getGameDetails(gameId: Int): GameDetails {
        val gameDetailsResponse = gamesApi.getGameDetails(gameId)
        return gameDetailsMapper.mapGameDetailsResponseToGameDetails(gameDetailsResponse)
    }

    suspend fun getGameImages(gameId: Int): GameImages {
        val gameScreenshotListResponse = gamesApi.getGameScreenshots(gameId)
        return gameDetailsMapper.mapGameScreenshotListToGameImages(gameScreenshotListResponse)
    }

}