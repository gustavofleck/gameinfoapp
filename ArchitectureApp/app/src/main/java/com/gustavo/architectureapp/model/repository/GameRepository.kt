package com.gustavo.architectureapp.model.repository

import com.gustavo.architectureapp.model.api.GamesApiService
import com.gustavo.architectureapp.model.games.GameDetails
import com.gustavo.architectureapp.model.games.GameList
import com.gustavo.architectureapp.model.mapper.GameDetailsMapper
import com.gustavo.architectureapp.model.mapper.GameListingMapper

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

}