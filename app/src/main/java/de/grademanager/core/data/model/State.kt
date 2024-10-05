package de.grademanager.core.data.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class State <T>(initial: T) {

    val _state = MutableStateFlow(initial)
    val state = _state.asStateFlow()

    fun update(other: T) {
        _state.update { other }
    }

    fun update(block: (T) -> T) {
        _state.update(block)
    }

    suspend fun collectLatest(action: suspend (value: T) -> Unit) {
        _state.collectLatest(action)
    }

    @Composable
    fun collectAsState(
        context: CoroutineContext = EmptyCoroutineContext
    ): State<T> = _state.asStateFlow().collectAsState(context = context)

    fun currentValue(): T {
        return _state.value
    }

}