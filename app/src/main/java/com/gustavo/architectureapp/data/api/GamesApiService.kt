package com.gustavo.architectureapp.data.api

import com.gustavo.architectureapp.data.model.GameDetailsResponse
import com.gustavo.architectureapp.data.model.GameListingResponse
import com.gustavo.architectureapp.data.model.GameScreenshotListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY = "cc65f8211a1c42b5b2089a4cfea764ec"

interface GamesApiService {

    @GET("games")
    suspend fun getGameList(
        @Query("search") search: String? = null,
        @Query("ordering") ordering: String? = null,
        @Query("page") page: Int? = null,
        @Query("key") apiKey: String = API_KEY
    ): GameListingResponse

    @GET("games/{id}")
    suspend fun getGameDetails(
        @Path("id") id: Int,
        @Query("key") apiKey: String = API_KEY
    ): GameDetailsResponse

    @GET("games/{id}/screenshots")
    suspend fun getGameScreenshots(
        @Path("id") id: Int,
        @Query("key") apiKey: String = API_KEY
    ): GameScreenshotListResponse
}
