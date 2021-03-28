package com.gustavo.architectureapp.model.games

import android.net.Uri
import com.gustavo.architectureapp.model.mapper.GameListingMapper
import com.gustavo.architectureapp.model.mapper.GameMapper
import com.gustavo.architectureapp.utils.createCompleteGameListingResponse
import com.gustavo.architectureapp.utils.createDefaultMappedGameListResponse
import com.gustavo.architectureapp.utils.createMappedGameList
import com.gustavo.architectureapp.utils.createNullGameListingResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class GameListingMapperTest {

    lateinit var gameListingMapper: GameListingMapper

    val gameMapperMock = mockk<GameMapper>()

    @BeforeEach
    fun setUp() {
        gameListingMapper = GameListingMapper(gameMapperMock)
    }

    @Nested
    @DisplayName("Given Game Listing Response retrieved")
    inner class GameListingMapperGameList {

        @Test
        fun `When response retrieved has null properties, should return mapped GameList without null properties`() {

            val gameListingResponse = createNullGameListingResponse()

            val expectedGameListMapped = createDefaultMappedGameListResponse()

            every {
                gameMapperMock.mapGameResponseListToListOfGames(any())
            } returns expectedGameListMapped

            val gameListMapped = gameListingMapper.mapGameListingResponseToGameList(gameListingResponse)

            assertTrue(gameListMapped.nextPage == 0)
            assertTrue(gameListMapped.gameItems.isEmpty())
        }

        @Test
        fun `When response retrieved has some data, should return mapped GameList with same properties of GameListingResponse`() {

            val nextPageUri = "https://api.rawg.io/api/games?page=2"
            val gameListingResponse = createCompleteGameListingResponse()
                .copy(nextPage = nextPageUri)

            val expectedGameListMapped = createMappedGameList().gameItems

            val expectedPageIndex = 2

            every {
                gameMapperMock.mapGameResponseListToListOfGames(gameListingResponse.gameList!!)
            } returns expectedGameListMapped

            mockkStatic(Uri::class)

            every {
                Uri.parse(nextPageUri).getQueryParameter("page")
            } returns "2"

            val gameListMapped = gameListingMapper.mapGameListingResponseToGameList(gameListingResponse)

            assertEquals(expectedGameListMapped, gameListMapped.gameItems)
            assertEquals(expectedPageIndex, gameListMapped.nextPage)

        }

        @Test
        fun `When response retrieved has some data and next page url is invalid, should throws an exception and return 0 as nextPage value`(){
            val nextPageUri = "rawg.io/api/games?"
            val gameListingResponse = createCompleteGameListingResponse()
                .copy(nextPage = nextPageUri)

            val expectedGameListMapped = createMappedGameList().gameItems

            val exception = Exception()

            val expectedPageIndex = 0

            every {
                gameMapperMock.mapGameResponseListToListOfGames(gameListingResponse.gameList!!)
            } returns expectedGameListMapped

            mockkStatic(Uri::class)

            every {
                Uri.parse(nextPageUri).getQueryParameter("page")
            } throws exception

            val gameListMapped = gameListingMapper.mapGameListingResponseToGameList(gameListingResponse)

            assertEquals(expectedGameListMapped, gameListMapped.gameItems)
            assertEquals(expectedPageIndex, gameListMapped.nextPage)

        }
    }
}