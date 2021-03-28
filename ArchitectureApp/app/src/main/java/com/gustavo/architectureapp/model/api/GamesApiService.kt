package com.gustavo.architectureapp.model.api

import com.gustavo.architectureapp.model.games.GameDetailsResponse
import com.gustavo.architectureapp.model.games.GameListingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GamesApiService {

    @GET("games")
    suspend fun getGameList(
        @Query("search") search: String? = null,
        @Query("ordering") ordering: String? = null,
        @Query("platforms") platform: Int,
        @Query("page") page: Int? = null
    ): GameListingResponse

    @GET("games/{id}")
    suspend fun getGameDetails(@Path("id") id: Int): GameDetailsResponse
}