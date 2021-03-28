package com.gustavo.architectureapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavo.architectureapp.data.games.GameDetails
import com.gustavo.architectureapp.data.interactor.GameDetailsInteractor
import com.gustavo.architectureapp.utils.provider.ThreadContextProvider
import com.gustavo.architectureapp.utils.result.SimpleResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameDetailsViewModel(
    private val gameDetailsInteractor: GameDetailsInteractor,
    private val threadContextProvider: ThreadContextProvider
) : ViewModel() {

    private val loadingLiveData = MutableLiveData<Boolean>()
    fun getLoadingLiveDataValue() : LiveData<Boolean> = loadingLiveData

    private val gameDetailsLiveData = MutableLiveData<GameDetails>()
    fun getGameDetailsLiveDataValue() : LiveData<GameDetails> = gameDetailsLiveData

    private val errorLiveData = MutableLiveData<Exception>()
    fun getErrorLiveDataValue() : LiveData<Exception> = errorLiveData

    fun getGameDetails(gameId: Int) {
        loadingLiveData.value = true
        viewModelScope.launch(threadContextProvider.io) {
            val simpleResult = gameDetailsInteractor.getGameDetails(gameId)
            withContext(threadContextProvider.ui) { resultHandler(simpleResult) }
        }
    }

    private fun resultHandler(result: SimpleResult<GameDetails>) {
        loadingLiveData.value = false
        when (result) {
            is SimpleResult.Success -> successStateHandler(result.data)
            is SimpleResult.Error -> errorStateHandler(result.exception)
        }
    }

    private fun errorStateHandler(exception: Exception) {
        errorLiveData.value = exception
    }

    private fun successStateHandler(gameDetails: GameDetails) {
        gameDetailsLiveData.value = gameDetails
    }

}