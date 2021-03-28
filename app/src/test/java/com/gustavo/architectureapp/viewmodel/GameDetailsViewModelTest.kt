package com.gustavo.architectureapp.viewmodel

import androidx.lifecycle.Observer
import com.gustavo.architectureapp.data.games.GameDetails
import com.gustavo.architectureapp.data.interactor.GameDetailsInteractor
import com.gustavo.architectureapp.utils.InstantExecutorExtension
import com.gustavo.architectureapp.utils.TestThreadContextProvider
import com.gustavo.architectureapp.utils.createCompleteMappedGameDetailsStub
import com.gustavo.architectureapp.utils.result.SimpleResult
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
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

    val observerLoadingLiveDataMock = mockk<Observer<Boolean>>(relaxed = true)

    val observerGameDetailsLiveDataMock = mockk<Observer<GameDetails>>(relaxed = true)

    val observerErrorLiveDataMock = mockk<Observer<Exception>>(relaxed = true)

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
        }

        @Test
        fun `Should change loading live data`(){

            gameDetailsViewModel.getLoadingLiveDataValue().observeForever(observerLoadingLiveDataMock)

            coEvery {
                gameDetailsInteractorMock.getGameDetails(gameId)
            } returns SimpleResult.Success(gameDetailsResultData)

            runBlocking { gameDetailsViewModel.getGameDetails(gameId) }

            verify {
                observerLoadingLiveDataMock.onChanged(true)
            }

        }

        @Test
        fun `When returns a success result, should change game details live data`(){

            gameDetailsViewModel.getGameDetailsLiveDataValue().observeForever(observerGameDetailsLiveDataMock)

            coEvery {
                gameDetailsInteractorMock.getGameDetails(gameId)
            } returns SimpleResult.Success(gameDetailsResultData)

            runBlocking { gameDetailsViewModel.getGameDetails(gameId) }

            verify {
                observerGameDetailsLiveDataMock.onChanged(gameDetailsResultData)
            }
        }

        @Test
        fun `When throws an exception, should change error live data`(){
            val exception = Exception()

            gameDetailsViewModel.getErrorLiveDataValue().observeForever(observerErrorLiveDataMock)

            coEvery {
                gameDetailsInteractorMock.getGameDetails(gameId)
            } returns SimpleResult.Error(exception)

            runBlocking { gameDetailsViewModel.getGameDetails(gameId) }

            verify {
                observerErrorLiveDataMock.onChanged(exception)
            }
        }

    }
    
}