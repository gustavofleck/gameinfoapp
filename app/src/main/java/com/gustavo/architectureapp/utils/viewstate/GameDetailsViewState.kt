package com.gustavo.architectureapp.utils.viewstate

import com.gustavo.architectureapp.data.model.GameDetails
import com.gustavo.architectureapp.data.model.GameImages
import com.gustavo.architectureapp.data.model.GameScreenshotResponse

sealed class GameDetailsViewState {
    object Loading : GameDetailsViewState()
    object Error : GameDetailsViewState()
    class ImagesSuccess(val data: GameImages) : GameDetailsViewState()
    class Success(val data: GameDetails) : GameDetailsViewState()
}