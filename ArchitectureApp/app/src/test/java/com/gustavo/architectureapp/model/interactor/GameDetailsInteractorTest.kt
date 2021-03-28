package com.gustavo.architectureapp.model.interactor

import com.gustavo.architectureapp.model.repository.GameRepository
import com.gustavo.architectureapp.utils.createCompleteMappedGameDetailsStub
import com.gustavo.architectureapp.utils.result.SimpleResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class GameDetailsInteractorTest {

    lateinit var gameDetailsInteractor: GameDetailsInteractor

    val gameRepositoryMock = mockk<GameRepository>()

    @BeforeEach
    fun setUp() {
        gameDetailsInteractor = GameDetailsInteractor(gameRepositoryMock)
    }

    @Nested
    @DisplayName("Given a Game Details data request")
    inner class GamesInteractorGameDetails {

        @Test
        fun `When the response is a success, should return Success with Game Details as data`(){
            val repositoryReturn = createCompleteMappedGameDetailsStub()
            val gameId = 1

            coEvery {
                gameRepositoryMock.getGameDetails(gameId)
            } returns repositoryReturn

            val successResult = runBlocking {
                gameDetailsInteractor.getGameDetails(gameId) as SimpleResult.Success
            }

            assertEquals(repositoryReturn, successResult.data)
        }

        @Test
        fun `When the responsse is a failure, should return Error with an exception`(){
            val exception = Exception()
            val gameId = 1

            coEvery {
                gameRepositoryMock.getGameDetails(gameId)
            } throws exception

            val errorResult = runBlocking {
                gameDetailsInteractor.getGameDetails(gameId) as SimpleResult.Error
            }

            assertEquals(exception, errorResult.exception)
        }

    }

}