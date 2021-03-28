package com.gustavo.architectureapp.utils.provider

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

open class ThreadContextProvider {
    open val ui: CoroutineContext by lazy { Dispatchers.Main }
    open val io: CoroutineContext by lazy { Dispatchers.IO }
}