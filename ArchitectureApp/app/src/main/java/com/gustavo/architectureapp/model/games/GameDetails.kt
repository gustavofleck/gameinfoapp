package com.gustavo.architectureapp.model.games

data class GameDetails(
    val name: String,
    val description: String,
    val backgroundImageUri: String,
    val released: String,
    val metacritic: Int
)