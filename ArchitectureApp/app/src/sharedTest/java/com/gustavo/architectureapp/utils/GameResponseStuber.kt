package com.gustavo.architectureapp.utils

import com.gustavo.architectureapp.model.games.GameItem
import com.gustavo.architectureapp.model.games.GameList
import com.gustavo.architectureapp.model.games.GameListingResponse
import com.gustavo.architectureapp.model.games.GameResponse

fun createNullGameListResponse(): List<GameResponse> {
    return listOf(
        createNullGameResponse(),
        createNullGameResponse()
    )
}

fun createNullGameResponse(): GameResponse {
    return GameResponse(
        null,
        null,
        null,
    )
}

fun createFilledGameListResponse(): List<GameResponse> {
    return listOf(
        createFilledGameResponse(),
        createFilledGameResponse()
    )
}

fun createFilledGameResponse(): GameResponse {
    return GameResponse(
        1,
        "Teste",
        "url",
    )
}

fun createNullGameListingResponse(): GameListingResponse {
    return GameListingResponse(
        null,
        null
    )
}

fun createCompleteGameListingResponse(): GameListingResponse {
    return GameListingResponse(
        "urlNextPage?page=2",
        createFilledGameListResponse()
    )
}

fun createMappedGameList(): GameList {
    return GameList(
        0,
        createMappedGameListResponse()
    )
}

fun createMappedGameListResponse(): List<GameItem> {
    return listOf(
        GameItem(
            1,
            "Teste",
            "url"
        )
    )
}

fun createDefaultMappedGameListResponse(): List<GameItem> {
    return listOf()
}