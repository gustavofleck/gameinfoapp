package com.gustavo.architectureapp.data.games

import com.gustavo.architectureapp.data.api.GamesApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class GameListingResponseTest {

    private val nextPageUrl = "https://nextpageurl.com"

    private lateinit var response: GameListingResponse

    @BeforeEach
    fun setUp() {
        runBlocking {
            response = createMockedService().getGameList(platform = 1)
        }
    }

    @Test
    fun `Returns next page url`() {
        assertEquals(nextPageUrl, response.nextPage)
    }

    @Test
    fun `Returns game list`() {
        assertFalse(response.gameList.isNullOrEmpty())
    }

    private fun mockResponseServer(): MockWebServer {
        val json = "{\"next\":\"$nextPageUrl\",\"results\":[{\"id\":" +
                "\"123\",\"name\":\"response test\",\"backgorund_image\":\"imageuri\"}]}"

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