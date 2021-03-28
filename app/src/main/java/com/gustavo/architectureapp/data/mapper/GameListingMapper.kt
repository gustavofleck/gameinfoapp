package com.gustavo.architectureapp.data.mapper

import android.net.Uri
import com.gustavo.architectureapp.data.games.GameList
import com.gustavo.architectureapp.data.games.GameListingResponse

class GameListingMapper(
    private val gameMapper: GameMapper
) {

    fun mapGameListingResponseToGameList(gameListingResponse: GameListingResponse): GameList {
        return GameList(
            mapNextPage(gameListingResponse.nextPage ?: ""),
            gameMapper.mapGameResponseListToListOfGames(
                gameListingResponse.gameList ?: listOf()
            )
        )
    }

    private fun mapNextPage(nextPage: String): Int = try {
        val page = Uri.parse(nextPage).getQueryParameter("page") ?: "0"
        page.toInt()
    } catch (exception: Exception) {
        0
    }

}