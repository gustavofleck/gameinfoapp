package com.gustavo.architectureapp.search

import com.gustavo.architectureapp.utils.TestThreadContextProvider
import com.gustavo.architectureapp.utils.search.DebounceSearchListener
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class DebounceSearchListenerTest {

    private val searchAction = mockk<(query: String?) -> Unit>(relaxed = true)
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)
    private val searchText = "Teste"



    @Test
    fun `When a text is filled on search field, should execute search action`() {
        val debounceSearchListener = buildDebounceSearchListener(0L)

        debounceSearchListener.onQueryTextChange(searchText)
        debounceSearchListener.onQueryTextChange("${searchText}e")

        verifySequence {
            searchAction(searchText)
            searchAction("${searchText}e")
        }
    }

    @Test
    fun `When is filled on search field, should execute search action`() {
        val debounceSearchListener = buildDebounceSearchListener(2000L)

        debounceSearchListener.onQueryTextChange(searchText)
        debounceSearchListener.onQueryTextChange("${searchText}e")

        Thread.sleep(2100)

        verify(exactly = 1) {
            searchAction(allAny())
        }
        verify {
            searchAction("${searchText}e")
        }
    }

    private fun buildDebounceSearchListener(delay: Long) = DebounceSearchListener(
        delayMillis = delay,
        searchQuery = searchAction,
        threadContextProvider = TestThreadContextProvider(),
        coroutineScope = testCoroutineScope
    )

}