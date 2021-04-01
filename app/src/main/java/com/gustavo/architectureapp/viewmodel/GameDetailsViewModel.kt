package com.gustavo.architectureapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavo.architectureapp.data.games.GameDetails
import com.gustavo.architectureapp.utils.viewstate.GameListViewState
import com.gustavo.architectureapp.data.interactor.GameDetailsInteractor
import com.gustavo.architectureapp.utils.provider.ThreadContextProvider
import com.gustavo.architectureapp.utils.result.SimpleResult
import com.gustavo.architectureapp.utils.viewstate.GameDetailsViewState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameDetailsViewModel(
    private val gameDetailsInteractor: GameDetailsInteractor,
    private val threadContextProvider: ThreadContextProvider
) : ViewModel() {

    private val viewStateLiveData = MutableLiveData<GameDetailsViewState>()
    fun getViewStateLiveData() : LiveData<GameDetailsViewState> = viewStateLiveData

    fun getGameDetails(gameId: Int) {
        viewStateLiveData.value = GameDetailsViewState.Loading
        viewModelScope.launch(threadContextProvider.io) {
            val simpleResult = gameDetailsInteractor.getGameDetails(gameId)
            withContext(threadContextProvider.ui) { resultHandler(simpleResult) }
        }
    }

    private fun resultHandler(result: SimpleResult<GameDetails>) {
        when (result) {
            is SimpleResult.Success -> successStateHandler(result.data)
            is SimpleResult.Error -> errorStateHandler(result.exception)
        }
    }

    private fun errorStateHandler(exception: Exception) {
        viewStateLiveData.value = GameDetailsViewState.Error
    }

    private fun successStateHandler(gameDetails: GameDetails) {
        viewStateLiveData.value = GameDetailsViewState.Success(gameDetails)
    }

}