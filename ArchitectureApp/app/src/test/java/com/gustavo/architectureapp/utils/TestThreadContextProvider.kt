package com.gustavo.architectureapp.utils

import com.gustavo.architectureapp.utils.provider.ThreadContextProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestThreadContextProvider : ThreadContextProvider() {
    override val ui: CoroutineContext = Dispatchers.Unconfined
    override val io: CoroutineContext = Dispatchers.Unconfined
}