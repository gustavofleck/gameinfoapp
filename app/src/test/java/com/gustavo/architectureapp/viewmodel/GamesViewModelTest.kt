package com.gustavo.architectureapp.viewmodel

import androidx.lifecycle.Observer
import com.gustavo.architectureapp.data.games.GameList
import com.gustavo.architectureapp.data.games.ViewState
import com.gustavo.architectureapp.data.interactor.GamesInteractor
import com.gustavo.architectureapp.utils.*
import com.gustavo.architectureapp.utils.pagination.PaginationController
import com.gustavo.architectureapp.utils.result.SimpleResult
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

// square mockwebserver
@ExtendWith(InstantExecutorExtension::class)
internal class GamesViewModelTest {

    lateinit var gamesViewModel: GamesViewModel

    val gamesInteractorMock = mockk<GamesInteractor>()

    val paginationControllerMock = mockk<PaginationController>(relaxed = true)

    val observerViewStateLiveDataMock = mockk<Observer<ViewState>>(relaxed = true)

    val loadingSlot = slot<ViewState.Loading>()

    @BeforeEach
    fun setUp() {
        gamesViewModel = GamesViewModel(
            gamesInteractorMock,
            paginationControllerMock,
            TestThreadContextProvider()
        )

        gamesViewModel.getViewStateLiveDataValue().observeForever(observerViewStateLiveDataMock)

    }

    @Nested
    @DisplayName("Given a view request for a list of games")
    inner class GamesViewModelListOfGamesSuccess {

        @Test
        fun `Should change loading live data`() {
            val platformId = 1
            val gameName = ""
            val expectedViewState = ViewState.Loading

            coEvery {
                gamesInteractorMock.getGameList(platformId)
            } returns SimpleResult.Success(createMappedGameList())

            runBlocking { gamesViewModel.getGames(platformId, gameName) }

            verify {
                observerViewStateLiveDataMock.onChanged(capture(loadingSlot))
            }
        }

        @Test
        fun `When returns a success result, should change game list live data`() {
            val gameListResultData = createMappedGameListResponse()
            val platformId = 1
            val nextPage = 2
            val gameName = ""
            val expectedViewState = slot<ViewState.Success>()

            coEvery {
                gamesInteractorMock.getGameList(platformId)
            } returns SimpleResult.Success(GameList(nextPage, gameListResultData))

            runBlocking { gamesViewModel.getGames(platformId, gameName) }

            verifySequence {
                observerViewStateLiveDataMock.onChanged(capture(loadingSlot))
                observerViewStateLiveDataMock.onChanged(capture(expectedViewState))
            }

            assertEquals(gameListResultData, expectedViewState.captured.data)
        }

        @Test
        fun `When returns a success result and has search query, should change game list search live data`() {
            val gameListResultData = createMappedGameListResponse()
            val platformId = 1
            val nextPage = 2
            val searchQuery = "teste"

            coEvery {
                gamesInteractorMock.getGameList(platformId, searchQuery = searchQuery)
            } returns SimpleResult.Success(GameList(nextPage, gameListResultData))

            runBlocking { gamesViewModel.getGames(platformId, searchQuery) }

            verify {
                observerViewStateLiveDataMock.onChanged(ViewState.Search(gameListResultData))
            }
        }

        @Test
        fun `When throws an exception, should change error live data`() {
            val exception = Exception()
            val platformId = 1
            val gameName = ""

            coEvery {
                gamesInteractorMock.getGameList(platformId)
            } returns SimpleResult.Error(exception)

            runBlocking { gamesViewModel.getGames(platformId, gameName) }

            verify {
                observerViewStateLiveDataMock.onChanged(ViewState.Error)
            }
        }
    }

    @Nested
    @DisplayName("Given a view request for next list of games")
    inner class GamesViewModelNextListOfGamesSuccess {

        @Test
        fun `When hasn't next page, should do nothing`() {
            val platformId = 1

            every {
                paginationControllerMock.hasNextPage()
            } returns false

            runBlocking { gamesViewModel.getNextPage(platformId) }

            verify { gamesInteractorMock wasNot Called }
        }

        @Test
        fun `When has next page, should change loading live data`() {
            val nextPage = 2
            val platformId = 1

            coEvery {
                gamesInteractorMock.getGameList(platformId, nextPage)
            } returns SimpleResult.Success(createMappedGameList())

            every {
                paginationControllerMock.hasNextPage()
            } returns true

            runBlocking { gamesViewModel.getNextPage(platformId) }

            verify {
                observerViewStateLiveDataMock.onChanged(ViewState.Loading)
            }
        }

        @Test
        fun `When returns a success result, should change game list live data`() {
            val gameListResultData = createMappedGameListResponse()
            val nextPage = 2
            val platformId = 1

            coEvery {
                gamesInteractorMock.getGameList(platformId, nextPage)
            } returns SimpleResult.Success(GameList(nextPage, gameListResultData))

            every {
                paginationControllerMock.hasNextPage()
            } returns true

            every {
                paginationControllerMock.getNextPage()
            } returns nextPage

            runBlocking { gamesViewModel.getNextPage(platformId) }

            verify {
                observerViewStateLiveDataMock.onChanged(ViewState.Success(gameListResultData))
            }
        }

        @Test
        fun `When throws an exception, should change error live data`() {
            val exception = Exception()
            val nextPage = 2
            val platformId = 1

            coEvery {
                gamesInteractorMock.getGameList(platformId, nextPage)
            } returns SimpleResult.Error(exception)

            every {
                paginationControllerMock.hasNextPage()
            } returns true

            every {
                paginationControllerMock.getNextPage()
            } returns nextPage

            runBlocking { gamesViewModel.getNextPage(platformId) }

            verify {
                observerViewStateLiveDataMock.onChanged(ViewState.Error)
            }
        }
    }
}