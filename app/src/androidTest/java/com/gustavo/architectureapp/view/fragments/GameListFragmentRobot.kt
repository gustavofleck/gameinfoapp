package com.gustavo.architectureapp.view.fragments

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
import com.gustavo.architectureapp.data.games.GameItem
import com.gustavo.architectureapp.utils.createMappedGameListResponse
import com.gustavo.architectureapp.view.MainActivity
import com.gustavo.architectureapp.view.adapters.PlatformAdapter
import com.gustavo.architectureapp.viewmodel.GamesViewModel
import com.gustavo.architectureapp.utils.image.ImageLoader
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
    private val platformAdapterMock = mockk<PlatformAdapter>(relaxed = true)
    private val imageLoaderMock = mockk<ImageLoader>(relaxed = true)

    private val bundle = Bundle()

    init {
        bundle.putInt("platformId", 1)
        loadKoinModules(module(override = true) {
            factory { platformAdapterMock }
            factory { imageLoaderMock }
            viewModel { viewModelMock }
        })
    }

    fun startWithLoading() {
        val loadingLiveData = MutableLiveData<Boolean>()

        every { viewModelMock.getGames(1, "") } answers { loadingLiveData.value = true }

        every { viewModelMock.getLoadingLiveDataValue() } returns loadingLiveData

        launchFragment()
    }

    fun startWithGameListData() {
        val gameListLiveData = MutableLiveData<List<GameItem>>()

        every { viewModelMock.getGames(1, "") } answers {
            gameListLiveData.value = createMappedGameListResponse()
        }

        every { viewModelMock.getGameListLiveDataValue() } returns gameListLiveData

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