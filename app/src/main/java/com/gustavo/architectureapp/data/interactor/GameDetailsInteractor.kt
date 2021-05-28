package com.gustavo.architectureapp.data.interactor

import com.gustavo.architectureapp.data.model.GameDetails
import com.gustavo.architectureapp.data.model.GameImages
import com.gustavo.architectureapp.data.model.GameScreenshotListResponse
import com.gustavo.architectureapp.data.repository.GameRepository
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

    suspend fun getGameImages(gameId: Int) : SimpleResult<GameImages> {
        return try {
            SimpleResult.Success(gameRepository.getGameImages(gameId))
        } catch (exception: Exception) {
            SimpleResult.Error(exception)
        }
    }
}