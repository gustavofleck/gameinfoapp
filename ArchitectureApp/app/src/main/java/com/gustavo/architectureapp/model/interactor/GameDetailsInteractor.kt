package com.gustavo.architectureapp.model.interactor

import com.gustavo.architectureapp.model.games.GameDetails
import com.gustavo.architectureapp.model.repository.GameRepository
import com.gustavo.architectureapp.utils.result.SimpleResult

class GameDetailsInteractor(
    private val gameRepository: GameRepository
) {
    
    suspend fun getGameDetails(gameId: Int) : SimpleResult<GameDetails> {
        return try {
            SimpleResult.Success(gameRepository.getGameDetails(gameId))
        } catch (exception: Exception) {
            SimpleResult.Error(exception)
        }
    }
}