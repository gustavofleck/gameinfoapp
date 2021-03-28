package com.gustavo.architectureapp.data.games

sealed class ViewState {
    object Loading : ViewState()
    object Error : ViewState()
    class Search(val data: List<GameItem>) : ViewState()
    class Success(val data: List<GameItem>) : ViewState()
}