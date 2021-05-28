package com.gustavo.architectureapp.view.fragments

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class GameListFragmentTest {

    // verificar chamadas dos métodos da viewmodel

    @Test
    fun when_start_fragment_should_call_getGames_and_show_loading_progress() {
        gameListRobot {
            startWithLoading()
        } check {
            checkLoadingDialogIsVisible()
        }
    }

    @Test
    fun when_start_fragment_should_call_getGames_and_retrieve_a_game_list() {
        val itemText = "Teste"

        gameListRobot {
            startWithGameListData()
        } check {
            checkItemInList(itemText) // criar variavel para garantir que o texto é o mesmo
        }
    }

}