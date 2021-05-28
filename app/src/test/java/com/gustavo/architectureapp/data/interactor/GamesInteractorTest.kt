package com.gustavo.architectureapp.data.interactor

import com.gustavo.architectureapp.data.model.GameList
import com.gustavo.architectureapp.data.repository.GameRepository
import com.gustavo.architectureapp.utils.result.SimpleResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.Exception

class GamesInteractorTest {

    lateinit var gamesInteractor: GamesInteractor

    val gameRepositoryMock = mockk<GameRepository>()

    @BeforeEach
    fun setUp() {
        gamesInteractor = GamesInteractor(gameRepositoryMock)
    }

    @Nested
    @DisplayName("Given a Game List data request")
    inner class GamesInteractorGameList {

        @Test
        fun `When the response is a success, should return Success with Game List as data`() {

            val repositoryReturn = GameList(0, listOf())
            val platformId = 1
            val nextPage = 1

            coEvery {
                gameRepositoryMock.getAllGames(platformId, nextPage)
            } returns repositoryReturn

            val successResult = runBlocking { gamesInteractor.getGameList(platformId) as SimpleResult.Success }

            assertEquals(repositoryReturn, successResult.data)

        }

        @Test
        fun `When the response is a failure, should return Error with an exception`() {

            val exception = Exception()
            val platformId = 1
            val nextPage = 1

            coEvery {
                gameRepositoryMock.getAllGames(platformId, nextPage)
            } throws exception

            val errorResult = runBlocking { gamesInteractor.getGameList(platformId) as SimpleResult.Error }

            assertEquals(exception, errorResult.exception)

        }
    }

    @Nested
    @DisplayName("Given next Game List data request")
    inner class GamesInteractorNextGameList {

        @Test
        fun `When the response is a success, should return Success with Game List as data`() {

            val nextPage = 2
            val platformId = 1
            val repositoryReturn = GameList(nextPage, listOf())

            coEvery {
                gameRepositoryMock.getAllGames(platformId, nextPage)
            } returns repositoryReturn

            val successResult = runBlocking {
                gamesInteractor.getGameList(platformId, repositoryReturn.nextPage) as SimpleResult.Success
            }

            assertEquals(repositoryReturn, successResult.data)

        }

        @Test
        fun `When the response is a failure, should return Error with an exception`() {

            val exception = Exception()
            val nextPageUrl = 2
            val platformId = 1

            coEvery {
                gameRepositoryMock.getAllGames(platformId, nextPageUrl)
            } throws exception

            val errorResult = runBlocking {
                gamesInteractor.getGameList(platformId, nextPageUrl) as SimpleResult.Error
            }

            assertEquals(exception, errorResult.exception)

        }
    }
}