package com.gustavo.architectureapp.model.games

data class GameList (
    val nextPage: Int,
    val gameItems: List<GameItem>
)