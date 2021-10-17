package com.gustavo.architectureapp.presentation.viewmodel

import androidx.lifecycle.Observer
import com.gustavo.architectureapp.data.interactor.GameDetailsInteractor
import com.gustavo.architectureapp.utils.InstantExecutorExtension
import com.gustavo.architectureapp.utils.TestThreadContextProvider
import com.gustavo.architectureapp.utils.createCompleteMappedGameDetailsStub
import com.gustavo.architectureapp.utils.result.SimpleResult
import com.gustavo.architectureapp.utils.viewstate.GameDetailsViewState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class GameDetailsViewModelTest {

    lateinit var gameDetailsViewModel: GameDetailsViewModel

    var gameDetailsInteractorMock = mockk<GameDetailsInteractor>()

    val observerViewStateLiveDataMock = mockk<Observer<GameDetailsViewState>>(relaxed = true)

    val loadingViewStateSlot = slot<GameDetailsViewState.Loading>()

    val errorViewStateSlot = slot<GameDetailsViewState.Error>()

    val successViewStateSlot = slot<GameDetailsViewState.Success>()

    @Nested
    @DisplayName("Given a view request for game details")
    inner class GameDetailsViewModelGameDetails {

        private val gameId = 1

        private val gameDetailsResultData = createCompleteMappedGameDetailsStub()

        @BeforeEach
        fun setUp() {
            gameDetailsViewModel = GameDetailsViewModel(
                gameDetailsInteractorMock,
                TestThreadContextProvider()
            )

            gameDetailsViewModel.getViewStateLiveData().observeForever(observerViewStateLiveDataMock)

        }

        @Test
        fun `Should change loading live data`(){
            coEvery {
                gameDetailsInteractorMock.getGameDetails(gameId)
            } returns SimpleResult.Success(gameDetailsResultData)

            runBlocking { gameDetailsViewModel.getGameDetails(gameId) }

            verify {
                observerViewStateLiveDataMock.onChanged(capture(loadingViewStateSlot))
            }

        }

        @Test
        fun `When returns a success result, should change game details live data`(){
            coEvery {
                gameDetailsInteractorMock.getGameDetails(gameId)
            } returns SimpleResult.Success(gameDetailsResultData)

            runBlocking { gameDetailsViewModel.getGameDetails(gameId) }

            verify {
                observerViewStateLiveDataMock.onChanged(capture(loadingViewStateSlot))
                observerViewStateLiveDataMock.onChanged(capture(successViewStateSlot))
            }

            assertEquals(gameDetailsResultData, successViewStateSlot.captured.data)
        }

        @Test
        fun `When throws an exception, should change error live data`(){
            val exception = Exception()

            coEvery {
                gameDetailsInteractorMock.getGameDetails(gameId)
            } returns SimpleResult.Error(exception)

            runBlocking { gameDetailsViewModel.getGameDetails(gameId) }

            verify {
                observerViewStateLiveDataMock.onChanged(capture(loadingViewStateSlot))
                observerViewStateLiveDataMock.onChanged(capture(errorViewStateSlot))
            }
        }

    }
    
}