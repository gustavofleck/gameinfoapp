package com.gustavo.architectureapp.utils

import com.gustavo.architectureapp.data.model.GameDetails
import com.gustavo.architectureapp.data.model.GameDetailsResponse

fun createCompleteGameDetailsResponseStub() = GameDetailsResponse(
    name = "Test",
    description = "Description test",
    backgroundImageUri = "imageUri",
    released = "2021-01-01",
    metacritic = "80",
    website = "adssa"
)

fun createNullGameDetailsResponseStub() = GameDetailsResponse(
    name = null,
    description = null,
    backgroundImageUri = null,
    released = null,
    metacritic = null,
    website = null
)

fun createCompleteMappedGameDetailsStub() = GameDetails(
    name = "Test",
    description = "Description test",
    backgroundImageUri = "imageUri",
    released = "01/01/2021",
    metacritic = "80",
    website = "adssa"
)