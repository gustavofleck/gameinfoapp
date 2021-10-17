package com.gustavo.architectureapp.presentation.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDeepLinkBuilder
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.gustavo.architectureapp.R
import com.gustavo.architectureapp.presentation.view.MainActivity
import com.gustavo.architectureapp.presentation.viewmodel.GamesViewModel
import com.gustavo.architectureapp.utils.image.ImageLoader
import com.gustavo.architectureapp.utils.viewstate.GameListViewState
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun gameListRobot(executeFun: GameListFragmentRobot.() -> Unit) =
    GameListFragmentRobot().apply {
        executeFun()
    }

class GameListFragmentRobot {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    private val viewModelMock = mockk<GamesViewModel>(relaxed = true)
    private val imageLoaderMock = mockk<ImageLoader>(relaxed = true)

    private val bundle = Bundle()

    init {
        bundle.putInt("platformId", 1)
        loadKoinModules(module(override = true) {
            factory { imageLoaderMock }
            viewModel { viewModelMock }
        })
    }

    fun startWithLoading() {
        val loadingLiveData = MutableLiveData<GameListViewState>()

        every { viewModelMock.getGames(1, "") } answers { loadingLiveData.value = GameListViewState.Loading }

        every { viewModelMock.getViewStateLiveDataValue() } returns loadingLiveData

        launchFragment()
    }

    fun startWithGameListData() {
        val gameListLiveData = MutableLiveData<GameListViewState>()

        every { viewModelMock.getGames(1, "") } answers {
//            gameListLiveData.value = GameListViewState.Success(createMappedGameListResponse())
        }

        every { viewModelMock.getViewStateLiveDataValue() } returns gameListLiveData

        launchFragment()
    }

    private fun launchFragment() {
        val launchFragmentIntent = buildLaunchFragmentIntent()
        activityRule.launchActivity(launchFragmentIntent)
    }

    private fun buildLaunchFragmentIntent(): Intent =
        NavDeepLinkBuilder(InstrumentationRegistry.getInstrumentation().targetContext)
            .setGraph(R.navigation.platform_navigation)
            .setComponentName(MainActivity::class.java)
            .setDestination(R.id.gameListFragment)
            .setArguments(bundle)
            .createTaskStackBuilder().intents[0]

    fun findItemAndClick(withText: String) {
        onView(withId(R.id.gameListRecyclerView))
            .perform(
                actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText(withText)),
                    scrollTo()
                )
            )
    }

    fun pressBack() {
        onView(isRoot()).perform(ViewActions.pressBack())
    }

    fun checkLoadingDialogIsVisible() {
        onView(withId(R.id.loading_progress)).check(matches(isDisplayed()))
    }

    fun checkItemInList(itemText: String) {
        onView(withText(itemText)).check(matches(isDisplayed()))
    }

    fun sleep(times: Long) = apply {
        Thread.sleep(times)
    }

    infix fun check(checkFun: GameListFragmentRobot.() -> Unit) {
        checkFun()
    }

}