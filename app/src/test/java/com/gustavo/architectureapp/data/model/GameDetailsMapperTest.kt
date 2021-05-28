package com.gustavo.architectureapp.data.model

import com.gustavo.architectureapp.data.mapper.GameDetailsMapper
import com.gustavo.architectureapp.utils.createCompleteGameDetailsResponseStub
import com.gustavo.architectureapp.utils.createNullGameDetailsResponseStub
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class GameDetailsMapperTest {

    lateinit var gameDetailsMapper: GameDetailsMapper

    @BeforeEach
    fun setUp() {
        gameDetailsMapper = GameDetailsMapper()
    }

    @Nested
    @DisplayName("Given new Game Details retrieved")
    inner class GameDetailsMapperGameDetailsResponse {

        @Test
        fun `When response retrieved has null properties, should return a Game Details without null properties`() {
            val gameDetailsResponse = createNullGameDetailsResponseStub()
            val expectedDefaultMetacriticValue = ""

            val mappedGameDetails = gameDetailsMapper
                .mapGameDetailsResponseToGameDetails(gameDetailsResponse)

            assertTrue(mappedGameDetails.name.isEmpty())
            assertTrue(mappedGameDetails.description.isEmpty())
            assertTrue(mappedGameDetails.backgroundImageUri.isEmpty())
            assertTrue(mappedGameDetails.released.isEmpty())
            assertEquals(expectedDefaultMetacriticValue, mappedGameDetails.metacritic)
        }

        @Test
        fun `When response retrieved has some data, should return Game Details with same fields that were mapped from Game Details Response`(){
            val gameDetailsResponse = createCompleteGameDetailsResponseStub()
            val expectedMappedReleaseDate = "01/01/2021"

            val mappedGameDetails = gameDetailsMapper
                .mapGameDetailsResponseToGameDetails(gameDetailsResponse)

            assertEquals(gameDetailsResponse.name, mappedGameDetails.name)
            assertEquals(gameDetailsResponse.description, mappedGameDetails.description)
            assertEquals(gameDetailsResponse.backgroundImageUri, mappedGameDetails.backgroundImageUri)
            assertEquals(expectedMappedReleaseDate, mappedGameDetails.released)
            assertEquals(gameDetailsResponse.metacritic, mappedGameDetails.metacritic)
        }

    }

}