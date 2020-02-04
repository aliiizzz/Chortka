package ir.aliiz.chortka.presentation

import kotlinx.coroutines.CoroutineDispatcher

data class AppDispatchers(val main: CoroutineDispatcher, val io: CoroutineDispatcher)