package com.gustavo.architectureapp.data.repository

import com.gustavo.architectureapp.data.api.GamesApiService
import com.gustavo.architectureapp.data.mapper.GameDetailsMapper
import com.gustavo.architectureapp.data.mapper.GameListingMapper
import com.gustavo.architectureapp.utils.createCompleteGameDetailsResponseStub
import com.gustavo.architectureapp.utils.createCompleteGameListingResponse
import com.gustavo.architectureapp.utils.createCompleteMappedGameDetailsStub
import com.gustavo.architectureapp.utils.createMappedGameList
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class GameRepositoryTest {

    lateinit var gameRepository: GameRepository

    val gamesApiMock = mockk<GamesApiService>()

    val gameListingMapperMock = mockk<GameListingMapper>()

    val gameDetailsMapperMock = mockk<GameDetailsMapper>()

    val exception = Exception()

    @BeforeEach
    fun setUp() {
        gameRepository = GameRepository(
            gamesApiMock,
            gameListingMapperMock,
            gameDetailsMapperMock
        )
    }

    @Nested
    @DisplayName("Given first Game Listing Response retrieved")
    inner class GameListingResponseGameList {

        @Test
        fun `When the response is a success, should return a mapped game list`() {
            val gameList = createCompleteGameListingResponse()
            val mappedGameList = createMappedGameList()
            val platformId = 1
            val page = 1

            coEvery {
                gamesApiMock.getGameList(any(), any(), platformId, page)
            } returns gameList

            every {
                gameListingMapperMock.mapGameListingResponseToGameList(gameList)
            } returns mappedGameList

            val gameListResult = runBlocking {
                gameRepository.getAllGames(platformId = platformId, nextPage = page)
            }

            assertEquals(mappedGameList, gameListResult)
        }

        @Test
        fun `When something goes wrong, should throws an exception`() {
            val gameList = createCompleteGameListingResponse()
            val mappedGameList = createMappedGameList()
            val platformId = 1
            val page = 1

            coEvery {
                gamesApiMock.getGameList(platform = platformId)
            } throws exception

            every {
                gameListingMapperMock.mapGameListingResponseToGameList(gameList)
            } returns mappedGameList

            assertThrows(Exception::class.java) {
                runBlocking { gameRepository.getAllGames(platformId = platformId, nextPage = page) }
            }
        }
    }

    @Nested
    @DisplayName("Given next Game Listing Response retrieved")
    inner class NextGameListingResponseGameList {

        @Test
        fun `When the response is a success, should return a mapped game list`() {
            val gameList = createCompleteGameListingResponse()
            val mappedGameList = createMappedGameList()
            val nextPage = 2
            val platformId = 1

            coEvery {
                gamesApiMock.getGameList(search = "", platform = platformId, page = nextPage)
            } returns gameList

            every {
                gameListingMapperMock.mapGameListingResponseToGameList(gameList)
            } returns mappedGameList

            val gameListResult = runBlocking {
                gameRepository.getAllGames(platformId, nextPage)
            }

            assertEquals(mappedGameList, gameListResult)
        }

        @Test
        fun `When something goes wrong, should throws an exception`() {
            val gameList = createCompleteGameListingResponse()
            val mappedGameList = createMappedGameList()
            val nextPage = 2
            val platformId = 1

            coEvery {
                gamesApiMock.getGameList(platform = platformId, page = nextPage)
            } throws exception

            every {
                gameListingMapperMock.mapGameListingResponseToGameList(gameList)
            } returns mappedGameList

            assertThrows(Exception::class.java) {
                runBlocking { gameRepository.getAllGames(platformId, nextPage) }
            }
        }
    }

    @Nested
    @DisplayName("Given game details retrieved")
    inner class GameDetailsResponseGameDetails {

        @Test
        fun `When the response is a success, should return mapped game details`() {
            val gameDetailsResponse = createCompleteGameDetailsResponseStub()
            val mappedGameDetails = createCompleteMappedGameDetailsStub()
            val gameId = 1

            coEvery {
                gamesApiMock.getGameDetails(gameId)
            } returns gameDetailsResponse

            every {
                gameDetailsMapperMock.mapGameDetailsResponseToGameDetails(gameDetailsResponse)
            } returns mappedGameDetails

            val gameDetailsResult = runBlocking {
                gameRepository.getGameDetails(gameId)
            }

            assertEquals(mappedGameDetails, gameDetailsResult)
        }

        @Test
        fun `When something goes wrong, should throws an exception`() {
            val gameDetailsResponse = createCompleteGameDetailsResponseStub()
            val mappedGameDetails = createCompleteMappedGameDetailsStub()
            val gameId = 1

            coEvery {
                gamesApiMock.getGameDetails(gameId)
            } throws exception

            every {
                gameDetailsMapperMock.mapGameDetailsResponseToGameDetails(gameDetailsResponse)
            } returns mappedGameDetails

            assertThrows(Exception::class.java) {
                runBlocking { gameRepository.getGameDetails(gameId) }
            }
        }
    }
}