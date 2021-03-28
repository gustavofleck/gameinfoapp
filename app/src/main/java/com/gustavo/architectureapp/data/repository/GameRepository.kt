package com.gustavo.architectureapp.data.repository

import com.gustavo.architectureapp.data.api.GamesApiService
import com.gustavo.architectureapp.data.games.GameDetails
import com.gustavo.architectureapp.data.games.GameList
import com.gustavo.architectureapp.data.mapper.GameDetailsMapper
import com.gustavo.architectureapp.data.mapper.GameListingMapper

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