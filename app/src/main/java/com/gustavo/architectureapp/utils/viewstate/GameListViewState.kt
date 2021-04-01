package com.gustavo.architectureapp.utils.viewstate

import com.gustavo.architectureapp.data.games.GameItem

sealed class GameListViewState {
    object Loading : GameListViewState()
    object Error : GameListViewState()
    class Search(val data: List<GameItem>) : GameListViewState()
    class Success(val data: List<GameItem>) : GameListViewState()
}