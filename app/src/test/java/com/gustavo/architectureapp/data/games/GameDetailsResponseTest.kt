package com.gustavo.architectureapp.data.games

import com.gustavo.architectureapp.data.api.GamesApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class GameDetailsResponseTest {

    private val gameName = "Name test"

    private val gameDescription = "Description test"

    private val gameBackgroundImage = "image uri"

    private val gameReleaseDate = "10/10/2010"

    private val gameMetacritic = "99"

    private lateinit var response: GameDetailsResponse

    @BeforeEach
    fun setUp() {
        runBlocking {
            response = createMockedService().getGameDetails(id = 1)
        }
    }

    @Test
    fun `Returns game name`() {
        assertEquals(gameName, response.name)
    }

    @Test
    fun `Returns game description`() {
        assertEquals(gameDescription, response.description)
    }

    @Test
    fun `Returns game background image`() {
        assertEquals(gameBackgroundImage, response.backgroundImageUri)
    }

    @Test
    fun `Returns game release date`() {
        assertEquals(gameReleaseDate, response.released)
    }

    @Test
    fun `Returns game metacritic`() {
        assertEquals(gameMetacritic, response.metacritic)
    }

    private fun mockResponseServer(): MockWebServer {
        val json = "{\"name\":\"$gameName\",\"description_raw\":\"$gameDescription\"," +
                "\"background_image\":\"$gameBackgroundImage\",\"released\":\"$gameReleaseDate\"," +
                "\"metacritic\":\"$gameMetacritic\"}"

        return MockWebServer().apply {
            enqueue(MockResponse().setBody(json))
        }
    }

    private fun createMockedService() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(mockResponseServer().url("/"))
        .build()
        .create(GamesApiService::class.java)

}