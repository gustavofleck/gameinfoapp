package com.gustavo.architectureapp.data.interactor

import com.gustavo.architectureapp.data.model.GameList
import com.gustavo.architectureapp.data.repository.GameRepository
import com.gustavo.architectureapp.utils.result.SimpleResult

class GamesInteractor(
        private val gameRepository: GameRepository
) {

    suspend fun getGameList(platformId: Int, nextPage: Int = 1, searchQuery: String = "") : SimpleResult<GameList> {
        return try {
            SimpleResult.Success(gameRepository.getAllGames(platformId, nextPage, searchQuery))
        } catch (exception: Exception) {
            SimpleResult.Error(exception)
        }
    }
}