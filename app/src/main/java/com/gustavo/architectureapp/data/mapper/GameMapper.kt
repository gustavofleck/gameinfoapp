package com.gustavo.architectureapp.data.mapper

import com.gustavo.architectureapp.data.model.GameItem
import com.gustavo.architectureapp.data.model.GameResponse

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
}