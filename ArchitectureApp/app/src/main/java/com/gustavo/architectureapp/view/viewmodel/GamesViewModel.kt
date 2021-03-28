package com.gustavo.architectureapp.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavo.architectureapp.model.games.GameItem
import com.gustavo.architectureapp.model.games.GameList
import com.gustavo.architectureapp.model.games.ViewState
import com.gustavo.architectureapp.model.interactor.GamesInteractor
import com.gustavo.architectureapp.utils.pagination.PaginationController
import com.gustavo.architectureapp.utils.result.SimpleResult
import com.gustavo.architectureapp.utils.provider.ThreadContextProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GamesViewModel(
    private val gamesInteractor: GamesInteractor,
    private val paginationController: PaginationController,
    private val threadContextProvider: ThreadContextProvider
) : ViewModel() {

    private var isSearch = false

    private var gameName = ""

    private val viewStateLiveData = MutableLiveData<ViewState>()
    fun getViewStateLiveDataValue(): LiveData<ViewState> = viewStateLiveData

    fun getGames(platformId: Int, gameName: String, search: Boolean = false) {
        viewStateLiveData.value = ViewState.Loading
        this.gameName = gameName
        isSearch = gameName.isNotEmpty()
        viewModelScope.launch(threadContextProvider.io) {
            val simpleResult = gamesInteractor.getGameList(
                platformId = platformId,
                searchQuery = gameName
            )
            withContext(threadContextProvider.ui) { resultHandler(simpleResult) }
        }
    }

    fun getNextPage(platformId: Int) {
        isSearch = false
        if (paginationController.hasNextPage()) {
            viewStateLiveData.value = ViewState.Loading
            viewModelScope.launch(threadContextProvider.io) {
                val nextSimpleResult =
                    gamesInteractor.getGameList(
                        platformId = platformId,
                        searchQuery = gameName,
                        nextPage = paginationController.getNextPage()
                    )
                withContext(threadContextProvider.ui) { resultHandler(nextSimpleResult) }
            }
        }
    }

    private fun resultHandler(result: SimpleResult<GameList>) {
        when (result) {
            is SimpleResult.Success -> successStateHandler(result.data)
            is SimpleResult.Error -> errorStateHandler(result.exception)
        }
    }

    private fun errorStateHandler(exception: Exception) {
        viewStateLiveData.value = ViewState.Error
    }

    private fun successStateHandler(gameList: GameList) {
        viewStateLiveData.value = setupSuccessViewState(gameList.gameItems)
        paginationController.setNextPage(gameList.nextPage)
    }

    private fun setupSuccessViewState(gameList: List<GameItem>): ViewState {
        return if (isSearch) ViewState.Search(gameList) else ViewState.Success(gameList)
    }

}