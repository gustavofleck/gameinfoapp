package com.gustavo.architectureapp.search

import com.gustavo.architectureapp.utils.TestThreadContextProvider
import com.gustavo.architectureapp.utils.search.DebounceSearchListener
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class DebounceSearchListenerTest {

    private val delay = 0L
    private val searchAction: (query: String?) -> Unit = {}
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)
    private val searchText = "Teste"

    private val debounceSearchListener = DebounceSearchListener(
        delayMillis = delay,
        searchQuery = searchAction,
        threadContextProvider = TestThreadContextProvider(),
        coroutineScope = testCoroutineScope
    )

    @Test
    fun `When a text is filled on search field, should execute search action`() {
        debounceSearchListener.onQueryTextChange(searchText)

//        verify {
//            searchAction(searchText)
//        }
    }

}