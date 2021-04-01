package com.gustavo.architectureapp.utils.viewstate

import com.gustavo.architectureapp.data.games.GameDetails

sealed class GameDetailsViewState {
    object Loading : GameDetailsViewState()
    object Error : GameDetailsViewState()
    class Success(val data: GameDetails) : GameDetailsViewState()
}