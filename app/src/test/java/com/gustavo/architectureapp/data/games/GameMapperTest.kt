package com.gustavo.architectureapp.data.games

import com.gustavo.architectureapp.data.mapper.GameMapper
import com.gustavo.architectureapp.utils.createFilledGameListResponse
import com.gustavo.architectureapp.utils.createNullGameListResponse
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

internal class GameMapperTest {

    lateinit var gameMapper: GameMapper

    @BeforeEach
    fun setUp() {
        gameMapper = GameMapper()
    }

    @Nested
    @DisplayName("Given new Game List Response retrieved")
    inner class GameMapperGameListResponse {

        @Test
        fun `When response retrieved has null properties, should return a list of Games without null properties`() {
            val gameListResponse = createNullGameListResponse()
            val expectedNumberDefaultValue = 0

            val gameMapped = gameMapper.mapGameResponseListToListOfGames(gameListResponse)

            for (index in gameMapped.indices) {
                assertEquals(expectedNumberDefaultValue, gameMapped[index].id)
                assertTrue(gameMapped[index].name.isEmpty())
                assertTrue(gameMapped[index].backgroundImageUri.isEmpty())
            }

        }

        @Test
        fun `When response retrieved has some data, should return Game with same fields that were mapped from Game Response`() {
            val gameResponse = createFilledGameListResponse()

            val gameMapped = gameMapper.mapGameResponseListToListOfGames(gameResponse)

            for (index in gameMapped.indices) {
                assertEquals(gameResponse[index].id, gameMapped[index].id)
                assertEquals(gameResponse[index].name, gameMapped[index].name)
                assertEquals(gameResponse[index].backgroundImageUri, gameMapped[index].backgroundImageUri)
            }
        }
    }
}