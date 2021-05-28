package com.gustavo.architectureapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavo.architectureapp.data.model.GameDetails
import com.gustavo.architectureapp.data.interactor.GameDetailsInteractor
import com.gustavo.architectureapp.data.model.GameImages
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

    fun getGameImages(gameId: Int) {
        viewStateLiveData.value = GameDetailsViewState.Loading
        viewModelScope.launch(threadContextProvider.io) {
            val simpleResult = gameDetailsInteractor.getGameImages(gameId)
            withContext(threadContextProvider.ui) { resultImageHandler(simpleResult) }
        }
    }

    private fun resultHandler(result: SimpleResult<GameDetails>) {
        when (result) {
            is SimpleResult.Success -> successStateHandler(result.data)
            is SimpleResult.Error -> errorStateHandler(result.exception)
        }
    }

    private fun resultImageHandler(result: SimpleResult<GameImages>) {
        when (result) {
            is SimpleResult.Success -> successImageHandler(result.data)
            is SimpleResult.Error -> errorStateHandler(result.exception)
        }
    }

    private fun successStateHandler(gameDetails: GameDetails) {
        viewStateLiveData.value = GameDetailsViewState.Success(gameDetails)
    }

    private fun successImageHandler(data: GameImages) {
        viewStateLiveData.value = GameDetailsViewState.ImagesSuccess(data)
    }

    private fun errorStateHandler(exception: Exception) {
        viewStateLiveData.value = GameDetailsViewState.Error
    }

}