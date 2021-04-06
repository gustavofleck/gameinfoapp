package com.gustavo.architectureapp.utils

import com.gustavo.architectureapp.data.games.GameDetails
import com.gustavo.architectureapp.data.games.GameDetailsResponse

fun createCompleteGameDetailsResponseStub() = GameDetailsResponse(
    name = "Test",
    description = "Description test",
    backgroundImageUri = "imageUri",
    released = "2021-01-01",
    metacritic = "80"
)

fun createNullGameDetailsResponseStub() = GameDetailsResponse(
    name = null,
    description = null,
    backgroundImageUri = null,
    released = null,
    metacritic = null
)

fun createCompleteMappedGameDetailsStub() = GameDetails(
    name = "Test",
    description = "Description test",
    backgroundImageUri = "imageUri",
    released = "01/01/2021",
    metacritic = "80"
)