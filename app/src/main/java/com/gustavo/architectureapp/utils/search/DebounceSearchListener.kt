package com.gustavo.architectureapp.utils.search

import androidx.appcompat.widget.SearchView
import com.gustavo.architectureapp.utils.provider.ThreadContextProvider
import kotlinx.coroutines.*

class DebounceSearchListener(
    private val delayMillis: Long,
    private val searchQuery: (query: String?) -> Unit,
    private val threadContextProvider: ThreadContextProvider = ThreadContextProvider(),
    private val coroutineScope: CoroutineScope
) : SearchView.OnQueryTextListener {

    private var searchJob: Job? = null

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchJob?.cancel()
        searchJob = coroutineScope.launch(threadContextProvider.ui) {
            newText?.let {
                delay(delayMillis)
                searchQuery(newText)
            }
        }

        return true
    }
}