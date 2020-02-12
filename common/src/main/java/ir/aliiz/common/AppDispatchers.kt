package ir.aliiz.common

import kotlinx.coroutines.CoroutineDispatcher

data class AppDispatchers(val main: CoroutineDispatcher, val io: CoroutineDispatcher)