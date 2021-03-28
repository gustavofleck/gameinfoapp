package com.gustavo.architectureapp.model.mapper

import com.gustavo.architectureapp.model.games.GameItem
import com.gustavo.architectureapp.model.games.GameResponse

class GameMapper {

    fun mapGameResponseListToListOfGames(gameResponseList: List<GameResponse>) : List<GameItem> {
        return gameResponseList.map { gameResponse ->
            GameItem(
                gameResponse.id ?: 0,
                gameResponse.name ?: "",
                gameResponse.backgroundImageUri ?: "",
            )
        }
    }

    fun mapGameResponseToGame(gameResponse: GameResponse) : GameItem {
        return GameItem(
            gameResponse.id ?: 0,
            gameResponse.name ?: "",
            gameResponse.backgroundImageUri ?: "",
        )
    }
}